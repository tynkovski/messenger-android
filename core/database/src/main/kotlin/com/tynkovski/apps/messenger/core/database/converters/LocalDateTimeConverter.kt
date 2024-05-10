package com.tynkovski.apps.messenger.core.database.converters

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

class LocalDateTimeConverter {
    @TypeConverter
    fun fromLocalDateTime(localDateTime: LocalDateTime?): Long? {
        return localDateTime?.toEpochSecond(ZoneOffset.UTC)
    }

    @TypeConverter
    fun toLocalDateTime(timestamp: Long?): LocalDateTime? {
        return timestamp?.let { LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) }
    }
}