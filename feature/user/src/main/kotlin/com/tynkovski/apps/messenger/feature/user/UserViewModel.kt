package com.tynkovski.apps.messenger.feature.user

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tynkovski.apps.messenger.core.domain.AddUserToContactsUsecase
import com.tynkovski.apps.messenger.core.domain.FindChatWithUserUsecase
import com.tynkovski.apps.messenger.core.domain.GetContactsUsecase
import com.tynkovski.apps.messenger.core.domain.GetMyselfUsecase
import com.tynkovski.apps.messenger.core.domain.GetUserUsecase
import com.tynkovski.apps.messenger.core.domain.RemoveUserFromContactsUsecase
import com.tynkovski.apps.messenger.core.model.Result
import com.tynkovski.apps.messenger.core.model.collector
import com.tynkovski.apps.messenger.core.model.data.User
import com.tynkovski.apps.messenger.core.ui.user.UserUi
import com.tynkovski.apps.messenger.feature.user.navigation.UserArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val findChatWithUserUsecase: FindChatWithUserUsecase,
    private val getMyselfUsecase: GetMyselfUsecase,
    private val getUserUsecase: GetUserUsecase,
    private val getContactsUsecase: GetContactsUsecase,
    private val removeUserFromContactsUsecase: RemoveUserFromContactsUsecase,
    private val addUserToContactsUsecase: AddUserToContactsUsecase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val userArgs: UserArgs = UserArgs(savedStateHandle)
    private val mContactsState: MutableStateFlow<Result<Set<User>>> =
        MutableStateFlow(Result.Loading)

    private val mSideEffect: MutableSharedFlow<UserSideEffect> = MutableSharedFlow()
    val sideEffect = mSideEffect.asSharedFlow()

    val userState: StateFlow<Result<UserUi>> = mContactsState.flatMapLatest { contactsState ->
        when (contactsState) {
            is Result.Error -> {
                flowOf(Result.Error(contactsState.exception))
            }

            Result.Loading -> {
                flowOf(Result.Loading)
            }

            is Result.Success -> {
                combine(
                    getUserUsecase(userArgs.userId),
                    getMyselfUsecase(),
                    ::Pair
                ).map<Pair<User, User>, Result<UserUi>> { (user, myself) ->
                    if (user.id == myself.id) {
                        Result.Success(UserUi.fromUser(user))
                    } else {
                        val inContacts = contactsState.data.any { contact -> contact.id == user.id }
                        Result.Success(UserUi.fromUser(user, inContacts))
                    }
                }.catch {
                    emit(Result.Error(it))
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        initialValue = Result.Loading,
        started = SharingStarted.WhileSubscribed(),
    )

    fun getContacts() {
        viewModelScope.launch {
            getContactsUsecase().collector(
                onSuccess = ::onSuccess,
                onError = ::onError,
                onLoading = ::onLoading
            )
        }
    }

    fun addToContacts(userId: Long) {
        viewModelScope.launch {
            addUserToContactsUsecase(userId).collector(
                onSuccess = ::onSuccess,
                onError = ::onError,
                onLoading = ::onLoading
            )
        }
    }

    fun removeFromContacts(userId: Long) {
        viewModelScope.launch {
            removeUserFromContactsUsecase(userId).collector(
                onSuccess = ::onSuccess,
                onError = ::onError,
                onLoading = ::onLoading
            )
        }
    }

    fun findChat(userId: Long) {
        viewModelScope.launch {
            findChatWithUserUsecase(userId).collector(
                onSuccess = {
                    mSideEffect.emit(UserSideEffect.NavigateToRoom(it.id))
                }
            )
        }
    }

    private suspend fun onSuccess(contacts: List<User>) {
        mContactsState.emit(Result.Success(contacts.toSet()))
    }

    private suspend fun onLoading() {
        mContactsState.emit(Result.Loading)
    }

    private suspend fun onError(exception: Throwable) {
        mContactsState.emit(Result.Error(exception))
    }

    init {
        getContacts()
    }
}