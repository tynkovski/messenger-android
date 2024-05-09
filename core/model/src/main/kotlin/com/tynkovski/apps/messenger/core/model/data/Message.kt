package com.tynkovski.apps.messenger.core.model.data

import java.time.LocalDateTime

data class Message(
    val id: Long,
    val senderId: Long,
    val roomId: Long,
    val text: String,
    val readBy: Set<Long>,
    val isDeleted: Boolean,
    val editedAt: LocalDateTime?,
    val sentAt: LocalDateTime
)
