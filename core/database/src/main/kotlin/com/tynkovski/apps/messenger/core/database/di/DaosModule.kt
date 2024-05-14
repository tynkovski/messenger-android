package com.tynkovski.apps.messenger.core.database.di

import com.tynkovski.apps.messenger.core.database.MessengerDatabase
import com.tynkovski.apps.messenger.core.database.dao.MessagesDao
import com.tynkovski.apps.messenger.core.database.dao.RoomsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {

    @Provides
    fun providesRoomsDao(
        database: MessengerDatabase,
    ): RoomsDao = database.roomsDao()

    @Provides
    fun providesMessagesDao(
        database: MessengerDatabase,
    ): MessagesDao = database.messagesDao()
}