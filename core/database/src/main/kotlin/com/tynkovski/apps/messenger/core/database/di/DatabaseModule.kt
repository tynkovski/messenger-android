package com.tynkovski.apps.messenger.core.database.di

import android.content.Context
import androidx.room.Room
import com.tynkovski.apps.messenger.core.database.MessengerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesNiaDatabase(
        @ApplicationContext context: Context,
    ): MessengerDatabase = Room.databaseBuilder(
        context,
        MessengerDatabase::class.java,
        "messenger-database",
    ).build()
}