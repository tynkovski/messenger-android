package com.tynkovski.apps.messenger.core.data.util

import com.tynkovski.apps.messenger.core.model.data.User
import com.tynkovski.apps.messenger.core.network.model.response.UserResponse
import java.time.LocalDateTime

object UserMapper {
    val responseToEntry: (UserResponse) -> User = {
        User(
            id = it.id,
            login = it.login,
            name = it.name,
            image = it.image,
            createdAt = LocalDateTime.parse(it.createdAt, timeFormatter),
            isDeleted = it.isDeleted
        )
    }
}

