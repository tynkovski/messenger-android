package com.tynkovski.apps.messenger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tynkovski.apps.messenger.core.datastore.TokenHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    tokenHolder: TokenHolder,
) : ViewModel() {
    val tokenState: StateFlow<TokenState> = tokenHolder
        .getTokenFlow()
        .map {
            TokenState.Success(it.accessToken.isNotEmpty())
        }.stateIn(
            scope = viewModelScope,
            initialValue = TokenState.Loading,
            started = SharingStarted.WhileSubscribed(),
        )

    sealed interface TokenState {
        data object Loading : TokenState
        data class Success(val hasToken: Boolean) : TokenState
        fun authenticated(): Boolean = this is Success && this.hasToken
    }
}