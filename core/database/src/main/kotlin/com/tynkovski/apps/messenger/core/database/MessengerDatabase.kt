package com.tynkovski.apps.messenger.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tynkovski.apps.messenger.core.database.converters.LocalDateTimeConverter
import com.tynkovski.apps.messenger.core.database.dao.MessagesDao
import com.tynkovski.apps.messenger.core.database.dao.RoomsDao
import com.tynkovski.apps.messenger.core.database.model.LastActionEntity
import com.tynkovski.apps.messenger.core.database.model.MessageEntity
import com.tynkovski.apps.messenger.core.database.model.RoomEntity

@Database(
    entities = [
        RoomEntity::class,
        LastActionEntity::class,
        MessageEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    LocalDateTimeConverter::class
)
abstract class MessengerDatabase : RoomDatabase() {
    abstract fun roomsDao(): RoomsDao
    abstract fun messagesDao(): MessagesDao
}