package com.tynkovski.apps.messenger.core.model.data

import java.time.LocalDateTime

data class User(
    val id: Long?,
    val login: String,
    val name: String?,
    val image: String?,
    val createdAt: LocalDateTime,
    val isDeleted: Boolean,
)