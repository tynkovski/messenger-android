package com.tynkovski.apps.messenger.core.data.util

import com.tynkovski.apps.messenger.core.model.data.AccessToken
import com.tynkovski.apps.messenger.core.model.data.Room
import com.tynkovski.apps.messenger.core.model.data.Token
import com.tynkovski.apps.messenger.core.model.data.User
import com.tynkovski.apps.messenger.core.network.model.AccessResponse
import com.tynkovski.apps.messenger.core.network.model.RoomLastActionResponse
import com.tynkovski.apps.messenger.core.network.model.RoomResponse
import com.tynkovski.apps.messenger.core.network.model.TokenResponse
import com.tynkovski.apps.messenger.core.network.model.UserResponse
import java.time.LocalDateTime

val userMapper: (UserResponse) -> User = {
    User(
        id = it.id,
        login = it.login,
        name = it.name,
        image = it.image,
        createdAt = LocalDateTime.parse(it.createdAt, timeFormatter),
        isDeleted = it.isDeleted
    )
}

val roomMapper: (RoomResponse) -> Room = {
    val actionMapper: (RoomLastActionResponse) -> Room.LastAction = {
        Room.LastAction(
            applicantId = it.authorId,
            actionType = it.actionType,
            description = it.description,
            actionDateTime = it.actionDateTime
        )
    }

    Room(
        id = it.id,
        name = it.name,
        image = it.image,
        users = it.users,
        moderators = it.moderators,
        lastAction = it.lastAction,
        deletedAt = it.deletedAt,
        createdAt = it.createdAt
    )
}

val tokenMapper: (TokenResponse) -> Token = {
    Token(it.accessToken, it.refreshToken)
}

val accessMapper: (AccessResponse) -> AccessToken = {
    AccessToken(it.accessToken)
}

val unitMapper: (Unit) -> Unit = { Unit }