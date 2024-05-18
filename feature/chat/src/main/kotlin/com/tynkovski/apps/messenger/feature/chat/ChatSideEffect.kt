package com.tynkovski.apps.messenger.feature.chat

sealed interface ChatSideEffect {
    data object ScrollDown: ChatSideEffect
}