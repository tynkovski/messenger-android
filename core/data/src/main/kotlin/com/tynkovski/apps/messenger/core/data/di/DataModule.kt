package com.tynkovski.apps.messenger.core.data.di

import com.tynkovski.apps.messenger.core.data.repository.AuthRepository
import com.tynkovski.apps.messenger.core.data.repository.ContactsRepository
import com.tynkovski.apps.messenger.core.data.repository.MessagesRepository
import com.tynkovski.apps.messenger.core.data.repository.RoomsRepository
import com.tynkovski.apps.messenger.core.data.repository.SearchRepository
import com.tynkovski.apps.messenger.core.data.repository.UsersRepository
import com.tynkovski.apps.messenger.core.data.repository.impl.AuthRepositoryImpl
import com.tynkovski.apps.messenger.core.data.repository.impl.ContactsRepositoryImpl
import com.tynkovski.apps.messenger.core.data.repository.impl.MessagesRepositoryImpl
import com.tynkovski.apps.messenger.core.data.repository.impl.RoomsRepositoryImpl
import com.tynkovski.apps.messenger.core.data.repository.impl.SearchRepositoryImpl
import com.tynkovski.apps.messenger.core.data.repository.impl.UsersRepositoryImpl
import com.tynkovski.apps.messenger.core.data.util.ConnectivityManagerNetworkMonitor
import com.tynkovski.apps.messenger.core.data.util.NetworkMonitor
import com.tynkovski.apps.messenger.core.data.util.TimeZoneBroadcastMonitor
import com.tynkovski.apps.messenger.core.data.util.TimeZoneMonitor
import com.tynkovski.apps.messenger.core.data.websockets.RoomsWebsocketClient
import com.tynkovski.apps.messenger.core.data.websockets.impl.RoomsWebsocketClientImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindsAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    internal abstract fun bindsUsersRepository(usersRepositoryImpl: UsersRepositoryImpl): UsersRepository

    @Binds
    internal abstract fun bindsMessagesRepository(messagesRepositoryImpl: MessagesRepositoryImpl): MessagesRepository


    @Binds
    internal abstract fun bindsRoomsRepository(roomsRepositoryImpl: RoomsRepositoryImpl): RoomsRepository

    @Binds
    internal abstract fun bindsRoomsWebsocketClient(impl: RoomsWebsocketClientImpl): RoomsWebsocketClient

    @Binds
    internal abstract fun bindsContactsRepository(contactsRepositoryImpl: ContactsRepositoryImpl): ContactsRepository

    @Binds
    internal abstract fun bindsSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

    @Binds
    internal abstract fun bindsNetworkMonitor(connectivityManagerNetworkMonitor: ConnectivityManagerNetworkMonitor): NetworkMonitor

    @Binds
    internal abstract fun bindsTimeZoneMonitor(timeZoneBroadcastMonitor: TimeZoneBroadcastMonitor): TimeZoneMonitor
}