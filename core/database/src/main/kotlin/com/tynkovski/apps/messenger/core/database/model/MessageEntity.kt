package com.tynkovski.apps.messenger.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
)