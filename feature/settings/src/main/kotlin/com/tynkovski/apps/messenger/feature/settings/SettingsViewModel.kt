package com.tynkovski.apps.messenger.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tynkovski.apps.messenger.core.datastore.TokenHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val tokenHolder: TokenHolder,
) : ViewModel() {
    fun signOut() {
        viewModelScope.launch {
            tokenHolder.logout()
        }
    }
}