package com.tynkovski.apps.messenger.feature.chats

import androidx.compose.runtime.Stable
import com.tynkovski.apps.messenger.core.model.data.Room
import java.time.format.DateTimeFormatter

@Stable
data class RoomUi(
    val id: Long,
    val name: String?,
    val image: String?,
    val lastAction: LastActionUi,
    val unreadMessages: Int,
) {
    companion object {
        private val messageTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

        private fun actionTypeMapper(type: Room.LastAction.ActionType) = when (type) {
            Room.LastAction.ActionType.USER_CREATE_ROOM -> LastActionUi.ActionTypeUi.USER_CREATE_ROOM
            Room.LastAction.ActionType.USER_RENAME_ROOM -> LastActionUi.ActionTypeUi.USER_RENAME_ROOM
            Room.LastAction.ActionType.USER_SENT_MESSAGE -> LastActionUi.ActionTypeUi.USER_SENT_MESSAGE
            Room.LastAction.ActionType.USER_INVITE_USER -> LastActionUi.ActionTypeUi.USER_INVITE_USER
            Room.LastAction.ActionType.USER_KICK_USER -> LastActionUi.ActionTypeUi.USER_KICK_USER
            Room.LastAction.ActionType.USER_QUIT -> LastActionUi.ActionTypeUi.USER_QUIT
            Room.LastAction.ActionType.USER_JOINED -> LastActionUi.ActionTypeUi.USER_JOINED
            Room.LastAction.ActionType.MAKE_MODERATOR -> LastActionUi.ActionTypeUi.MAKE_MODERATOR
        }

        private fun actionMapper(authorName: String, action: Room.LastAction) = LastActionUi(
            authorName = authorName,
            actionType = actionTypeMapper(action.actionType),
            description = action.description,
            actionDateTime = messageTimeFormatter.format(action.actionDateTime)
        )

        fun fromRoom(room: Room, authorName: String, unreadMessages: Int) = RoomUi(
            id = room.id,
            name = room.name,
            image = room.image,
            lastAction = actionMapper(authorName, room.lastAction),
            unreadMessages = unreadMessages
        )
    }

    @Stable
    data class LastActionUi(
        val authorName: String,
        val actionType: ActionTypeUi,
        val description: String?,
        val actionDateTime: String,
    ) {
        enum class ActionTypeUi {
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