package com.tynkovski.apps.messenger.core.ui.chat

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

sealed interface ChatState {
    data object Loading : ChatState
    data class Error(val exception: Throwable) : ChatState
    data class Success(
        val image: String?,
        val name: String?,
        val users: List<String>,
        val paging: Flow<PagingData<MessageUi>>
    ) : ChatState
}