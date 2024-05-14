package com.tynkovski.apps.messenger.feature.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tynkovski.apps.messenger.core.domain.AddUserToContactsUsecase
import com.tynkovski.apps.messenger.core.domain.CreateChatUsecase
import com.tynkovski.apps.messenger.core.domain.FindUserUsecase
import com.tynkovski.apps.messenger.core.domain.GetContactsUsecase
import com.tynkovski.apps.messenger.core.domain.RemoveUserFromContactsUsecase
import com.tynkovski.apps.messenger.core.model.collector
import com.tynkovski.apps.messenger.core.model.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SEARCH_QUERY = "search_query"

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val addUserToContactsUsecase: AddUserToContactsUsecase,
    private val removeUserFromContactsUsecase: RemoveUserFromContactsUsecase,
    private val getContactsUsecase: GetContactsUsecase,
    private val createChatUsecase: CreateChatUsecase,
    private val findUserUsecase: FindUserUsecase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val mSideEffect: MutableSharedFlow<SearchSideEffect> = MutableSharedFlow()
    val sideEffect = mSideEffect.asSharedFlow()

    val queryState = savedStateHandle.getStateFlow(SEARCH_QUERY, "")

    private val mContactsState: MutableStateFlow<Set<User>> = MutableStateFlow(setOf())
    val contactsState = mContactsState.asStateFlow()

    fun setQuery(value: String) {
        savedStateHandle[SEARCH_QUERY] = value
    }

    fun createRoom(userId: Long) {
        viewModelScope.launch {
            createChatUsecase(userId).collector(
                onSuccess = {
                    mSideEffect.emit(SearchSideEffect.NavigateToChat(it.id))
                },
            )
        }
    }

    fun addToContacts(userId: Long) {
        viewModelScope.launch {
            addUserToContactsUsecase(userId).collector(
                onSuccess = {
                    mContactsState.emit(it.toSet())
                }
            )
        }
    }

    fun removeFromContacts(userId: Long) {
        viewModelScope.launch {
            removeUserFromContactsUsecase(userId).collector(
                onSuccess = {
                    mContactsState.emit(it.toSet())
                }
            )
        }
    }

    fun getContacts() {
        viewModelScope.launch {
            getContactsUsecase().collector(
                onSuccess = {
                    mContactsState.emit(it.toSet())
                }
            )
        }
    }

    val searchState: StateFlow<SearchUiState> = combine(
        queryState,
        contactsState,
        ::Pair
    ).flatMapLatest { (query, contacts) ->
        if (query.isEmpty()) {
            flowOf(SearchUiState.EmptyQuery)
        } else {
            findUserUsecase(query)
                .map<User, SearchUiState> { user ->
                    val inContacts = contacts.any { contact -> contact.id == user.id }
                    SearchUiState.Success(user, inContacts)
                }
                .onStart { emit(SearchUiState.Loading) }
                .catch { emit(SearchUiState.LoadFailed(it)) }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SearchUiState.Loading,
    )

    init {
        getContacts()
    }
}