package com.tynkovski.apps.messenger.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tynkovski.apps.messenger.core.data.repository.UsersRepository
import com.tynkovski.apps.messenger.core.datastore.TokenHolder
import com.tynkovski.apps.messenger.core.model.Result
import com.tynkovski.apps.messenger.core.model.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val tokenHolder: TokenHolder,
    private val usersRepository: UsersRepository
) : ViewModel() {

    val userState = usersRepository
        .getUser()
        .map<User, Result<User>> { user -> Result.Success(user) }
        .catch { emit(Result.Error(it)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Result.Loading,
        )

    fun signOut() {
        viewModelScope.launch {
            tokenHolder.logout()
        }
    }
}