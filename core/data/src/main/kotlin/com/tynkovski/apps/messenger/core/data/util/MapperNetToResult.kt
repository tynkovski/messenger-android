package com.tynkovski.apps.messenger.core.data.util

import com.tynkovski.apps.messenger.core.model.data.AccessToken
import com.tynkovski.apps.messenger.core.model.data.Token
import com.tynkovski.apps.messenger.core.model.data.User
import com.tynkovski.apps.messenger.core.network.model.AccessResponse
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

val tokenMapper: (TokenResponse) -> Token = {
    Token(it.accessToken, it.refreshToken)
}

val accessMapper: (AccessResponse) -> AccessToken = {
    AccessToken(it.accessToken)
}

val unitMapper: (Unit) -> Unit = { Unit }