package com.tynkovski.apps.messenger.core.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "rooms")
data class RoomEntity(
    @PrimaryKey(autoGenerate = false) val id: Long,
    val name: String?,
    val image: String?,
    val users: String,
    val moderators: String,
    val isDeleted: Boolean,
    @Embedded val lastAction: LastActionEntity,
    val createdAt: LocalDateTime
)

@Entity(tableName = "last_actions")
data class LastActionEntity(
    @PrimaryKey(autoGenerate = true) val lastActionId: Long,
    val applicantId: Long,
    val actionType: String,
    val description: String?,
    val actionDateTime: LocalDateTime
)