package com.tynkovski.apps.messenger.core.ui.chat

sealed interface ChatState {
    data object Loading : ChatState
    data class Error(val exception: Throwable) : ChatState
    data class Success(
        val image: String?,
        val name: String?,
        val users: List<String>
    ) : ChatState
}