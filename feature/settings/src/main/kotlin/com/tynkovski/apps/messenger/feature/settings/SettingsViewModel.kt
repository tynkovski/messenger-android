package com.tynkovski.apps.messenger.feature.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tynkovski.apps.messenger.core.data.repository.UsersRepository
import com.tynkovski.apps.messenger.core.datastore.TokenHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val tokenHolder: TokenHolder,
    private val usersRepository: UsersRepository
) : ViewModel() {
    init {

    }

    fun getUser() {
        viewModelScope.launch {
            usersRepository
                .getUser()
                .onEach {
                    Log.d("ViewModel.getUser", it.toString())
                }
                .catch {
                    Log.d("ViewModel.getUser", it.message.toString())
                }
                .collect()
        }
    }

    fun signOut() {
        viewModelScope.launch {
            tokenHolder.logout()
        }
    }
}