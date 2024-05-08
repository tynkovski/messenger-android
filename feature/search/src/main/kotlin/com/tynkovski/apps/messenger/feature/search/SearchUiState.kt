package com.tynkovski.apps.messenger.feature.search

import com.tynkovski.apps.messenger.core.model.data.User

sealed interface SearchUiState {
    data object EmptyQuery : SearchUiState
    data object Loading : SearchUiState
    data object LoadFailed : SearchUiState
    data class Success(val user: User) : SearchUiState
}