package com.tynkovski.apps.messenger.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val senderId: Long,
    val roomId: Long,
    val text: String,
    val readBy: String,
    val isDeleted: Boolean,
    val editedAt: LocalDateTime?,
    val sentAt: LocalDateTime
)