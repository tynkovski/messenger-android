package com.tynkovski.apps.messenger.feature.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tynkovski.apps.messenger.core.domain.CreateChatUsecase
import com.tynkovski.apps.messenger.core.domain.FindUserUsecase
import com.tynkovski.apps.messenger.core.model.Result
import com.tynkovski.apps.messenger.core.model.data.User
import com.tynkovski.apps.messenger.core.model.onError
import com.tynkovski.apps.messenger.core.model.onLoading
import com.tynkovski.apps.messenger.core.model.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SEARCH_QUERY = "searchQuery"

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val createChatUsecase: CreateChatUsecase,
    private val findUserUsecase: FindUserUsecase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val queryState = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")

    fun setQuery(value: String) {
        savedStateHandle[SEARCH_QUERY] = value
    }

    fun createRoom(collocutorId: Long) {
        viewModelScope.launch {

        }
    }

    val searchResultUiState: StateFlow<SearchUiState> = queryState.flatMapLatest { query ->
        if (query.isEmpty()) {
            flowOf(SearchUiState.EmptyQuery)
        } else {
            findUserUsecase(query)
                .onStart {
                    SearchUiState.Loading
                }
                .map<User, SearchUiState> {
                    SearchUiState.Success(it)
                }.catch {
                    emit(SearchUiState.LoadFailed)
                }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SearchUiState.Loading,
    )
}