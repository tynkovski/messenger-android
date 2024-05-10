package com.tynkovski.apps.messenger.core.model.data

import java.time.LocalDateTime

data class Room(
    val id: Long,
    val name: String?,
    val image: String?,
    val users: Set<Long>,
    val moderators: Set<Long>,
    val isDeleted: Boolean,
    val lastAction: LastAction,
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
            MAKE_MODERATOR;
            companion object {
                fun fromString(string: String) = when (string) {
                    "USER_CREATE_ROOM" -> USER_CREATE_ROOM
                    "USER_RENAME_ROOM" -> USER_RENAME_ROOM
                    "USER_SENT_MESSAGE" -> USER_SENT_MESSAGE
                    "USER_INVITE_USER" -> USER_INVITE_USER
                    "USER_KICK_USER" -> USER_KICK_USER
                    "USER_QUIT" -> USER_QUIT
                    "USER_JOINED" -> USER_JOINED
                    "MAKE_MODERATOR" -> MAKE_MODERATOR
                    else -> USER_CREATE_ROOM
                }
            }
        }
    }
}