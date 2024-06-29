package com.tynkovski.apps.messenger.feature.chats

sealed interface ChatsSideEffect {
    data class NavigateToChat(val chatId: Long) :ChatsSideEffect
}