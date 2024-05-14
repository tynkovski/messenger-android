package com.tynkovski.apps.messenger.core.ui.user

import androidx.compose.runtime.Stable
import com.tynkovski.apps.messenger.core.model.data.User
import java.time.format.DateTimeFormatter

sealed interface UserUi {
    @Stable
    data class Somebody(
        val id: Long,
        val login: String,
        val name: String?,
        val image: String?,
        val createdAt: String,
        val isDeleted: Boolean,
        val inContacts: Boolean,
    ) : UserUi

    @Stable
    data class Myself(
        val id: Long,
        val login: String,
        val name: String?,
        val image: String?,
        val createdAt: String,
    ) : UserUi

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd"
        private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT)

        fun fromUser(user: User, inContacts: Boolean)  = with(user) {
            Somebody(
                id = id,
                login = login,
                name = name,
                image = image,
                createdAt = timeFormatter.format(createdAt),
                isDeleted = isDeleted,
                inContacts = inContacts
            )
        }

        fun fromUser(user: User)  = with(user) {
            Myself(
                id = id,
                login = login,
                name = name,
                image = image,
                createdAt = timeFormatter.format(createdAt)
            )
        }
    }
}

