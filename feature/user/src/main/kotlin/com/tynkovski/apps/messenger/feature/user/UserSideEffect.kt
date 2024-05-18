package com.tynkovski.apps.messenger.feature.user

sealed interface UserSideEffect {
    data class NavigateToRoom(val roomId: Long): UserSideEffect
}