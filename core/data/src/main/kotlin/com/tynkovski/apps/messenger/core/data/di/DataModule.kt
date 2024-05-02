package com.tynkovski.apps.messenger.core.data.di

import com.tynkovski.apps.messenger.core.data.repository.*
import com.tynkovski.apps.messenger.core.data.repository.impl.*
import com.tynkovski.apps.messenger.core.data.util.ConnectivityManagerNetworkMonitor
import com.tynkovski.apps.messenger.core.data.util.NetworkMonitor
import com.tynkovski.apps.messenger.core.data.util.TimeZoneBroadcastMonitor
import com.tynkovski.apps.messenger.core.data.util.TimeZoneMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindsAuthRepository(
        authRepository: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    internal abstract fun bindsUsersRepository(
        usersRepository: UsersRepositoryImpl
    ): UsersRepository

    @Binds
    internal abstract fun bindsMessagesRepository(
        messagesRepository: MessagesRepositoryImpl
    ): MessagesRepository

    @Binds
    internal abstract fun bindsRoomsRepository(
        roomsRepository: RoomsRepositoryImpl
    ): RoomsRepository

    @Binds
    internal abstract fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor

    @Binds
    internal abstract fun binds(impl: TimeZoneBroadcastMonitor): TimeZoneMonitor
}