package com.tynkovski.apps.messenger.core.model.data

import java.time.LocalDateTime

data class Room(
    val id: Long,
    val name: String?,
    val image: String?,
    val users: Set<Long>,
    val moderators: Set<Long>,
    val lastAction: LastAction?,
    val isDeleted: Boolean,
    val createdAt: LocalDateTime,
) {
    data class LastAction(
        val applicantId: Long,
        val actionType: ActionType,
        val description: String?,
        val actionDateTime: LocalDateTime,
    ) {
        enum class ActionType {
            USER_CREATE_ROOM,
            USER_RENAME_ROOM,
            USER_SENT_MESSAGE,
            USER_INVITE_USER,
            USER_KICK_USER,
            USER_QUIT,
            USER_JOINED,
            MAKE_MODERATOR
        }
    }
}