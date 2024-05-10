package com.tynkovski.apps.messenger.core.data.util

import com.tynkovski.apps.messenger.core.database.model.LastActionEntity
import com.tynkovski.apps.messenger.core.database.model.RoomEntity
import com.tynkovski.apps.messenger.core.model.data.Room
import com.tynkovski.apps.messenger.core.network.model.response.RoomLastActionResponse
import com.tynkovski.apps.messenger.core.network.model.response.RoomResponse
import java.time.LocalDateTime

object RoomMapper {
    val localToEntry: (RoomEntity) -> Room = { room ->
        val actionMapper: (LastActionEntity) -> Room.LastAction = { action ->
            Room.LastAction(
                applicantId = action.applicantId,
                actionType = Room.LastAction.ActionType.fromString(action.actionType),
                description = action.description,
                actionDateTime = action.actionDateTime
            )
        }

        val setMapper: (String) -> Set<Long> = { string ->
            if (string.isEmpty()) setOf()
            else string.split(",").map { it.toLong() }.toSet()
        }

        Room(
            id = room.id,
            name = room.name,
            image = room.image,
            users = setMapper(room.users),
            moderators = setMapper(room.moderators),
            isDeleted = room.isDeleted,
            lastAction = actionMapper(room.lastAction),
            createdAt = room.createdAt
        )
    }

    val localListToEntryList: (List<RoomEntity>) -> List<Room> = { rooms ->
        rooms.map(localToEntry)
    }

    val entryToLocal: (Room) -> RoomEntity = { room ->
        val actionMapper: (Room.LastAction) -> LastActionEntity = { action ->
            LastActionEntity(
                lastActionId = room.id,
                applicantId = action.applicantId,
                actionType = action.actionType.toString(),
                description = action.description,
                actionDateTime = action.actionDateTime
            )
        }
        RoomEntity(
            id = room.id,
            name = room.name,
            image = room.image,
            users = room.users.joinToString(","),
            moderators = room.moderators.joinToString(","),
            isDeleted = room.isDeleted,
            lastAction = actionMapper(room.lastAction),
            createdAt = room.createdAt
        )
    }

    val remoteToEntry: (RoomResponse) -> Room = { room ->
        val actionMapper: (RoomLastActionResponse) -> Room.LastAction = { action ->
            Room.LastAction(
                applicantId = action.authorId,
                actionType = Room.LastAction.ActionType.fromString(action.actionType),
                description = action.description,
                actionDateTime = LocalDateTime.parse(action.actionDateTime, timeFormatter)
            )
        }

        Room(
            id = room.id,
            name = room.name,
            image = room.image,
            users = room.users.toSet(),
            moderators = room.moderators.toSet(),
            isDeleted = room.isDeleted,
            lastAction = actionMapper(room.lastAction),
            createdAt = LocalDateTime.parse(room.createdAt, timeFormatter)
        )
    }

    val entryToRemote: (Room) -> RoomResponse = { room ->
        val actionMapper: (Room.LastAction) -> RoomLastActionResponse = { action ->
            RoomLastActionResponse(
                authorId = action.applicantId,
                actionType = action.actionType.toString(),
                description = action.description,
                actionDateTime = timeFormatter.format(action.actionDateTime)
            )
        }
        RoomResponse(
            id = room.id,
            name = room.name,
            image = room.image,
            users = room.users.toList(),
            moderators = room.users.toList(),
            isDeleted = room.isDeleted,
            lastAction = actionMapper(room.lastAction),
            createdAt = timeFormatter.format(room.createdAt)
        )
    }

}