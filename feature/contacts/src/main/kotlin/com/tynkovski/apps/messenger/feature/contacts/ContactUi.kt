package com.tynkovski.apps.messenger.feature.contacts

import androidx.compose.runtime.Stable
import com.tynkovski.apps.messenger.core.model.data.User

@Stable
data class ContactUi(
    val id: Long,
    val login: String,
    val name: String?,
    val image: String?,
) {
    companion object {
        fun fromUser(user: User): ContactUi {
            return ContactUi(
                id = user.id, login = user.login, name = user.name, image = user.image
            )
        }
    }
}