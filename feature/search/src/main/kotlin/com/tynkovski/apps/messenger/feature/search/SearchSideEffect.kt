package com.tynkovski.apps.messenger.feature.search

sealed interface SearchSideEffect {
    data class NavigateToUser(val userId: Long): SearchSideEffect
    data class NavigateToChat(val chatId: Long): SearchSideEffect
}