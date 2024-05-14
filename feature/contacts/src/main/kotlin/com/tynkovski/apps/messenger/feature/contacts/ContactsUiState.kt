package com.tynkovski.apps.messenger.feature.contacts

import androidx.compose.runtime.Stable

sealed interface ContactsUiState {
    data object Empty : ContactsUiState

    data object Loading : ContactsUiState

    @Stable
    data class Error(val exception: Throwable) : ContactsUiState

    @Stable
    data class Success(val contacts: List<ContactUi>) : ContactsUiState
}