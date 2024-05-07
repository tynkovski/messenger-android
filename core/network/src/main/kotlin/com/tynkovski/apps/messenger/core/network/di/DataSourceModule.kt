package com.tynkovski.apps.messenger.core.network.di

import com.tynkovski.apps.messenger.core.network.AuthDataSource
import com.tynkovski.apps.messenger.core.network.ContactsDataSource
import com.tynkovski.apps.messenger.core.network.MessagesDataSource
import com.tynkovski.apps.messenger.core.network.RoomsDataSource
import com.tynkovski.apps.messenger.core.network.UserDataSource
import com.tynkovski.apps.messenger.core.network.impl.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {
    @Binds
    fun bindsAuthDataSource(impl: AuthDataSourceImpl): AuthDataSource
    @Binds
    fun bindsUserDataSource(impl: UserDataSourceImpl): UserDataSource
    @Binds
    fun bindsContactsDataSource(impl: ContactsDataSourceImpl): ContactsDataSource
    @Binds
    fun bindsMessagesDataSource(impl: MessagesDataSourceImpl): MessagesDataSource
    @Binds
    fun bindsRoomsDataSource(impl: RoomsDataSourceImpl): RoomsDataSource
}