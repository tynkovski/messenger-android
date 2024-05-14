package com.tynkovski.apps.messenger.core.network.di

import com.tynkovski.apps.messenger.core.network.AuthDataSource
import com.tynkovski.apps.messenger.core.network.ContactsDataSource
import com.tynkovski.apps.messenger.core.network.MessagesDataSource
import com.tynkovski.apps.messenger.core.network.RoomsDataSource
import com.tynkovski.apps.messenger.core.network.SearchDataSource
import com.tynkovski.apps.messenger.core.network.UserDataSource
import com.tynkovski.apps.messenger.core.network.impl.AuthDataSourceImpl
import com.tynkovski.apps.messenger.core.network.impl.ContactsDataSourceImpl
import com.tynkovski.apps.messenger.core.network.impl.MessagesDataSourceImpl
import com.tynkovski.apps.messenger.core.network.impl.RoomsDataSourceImpl
import com.tynkovski.apps.messenger.core.network.impl.SearchDataSourceImpl
import com.tynkovski.apps.messenger.core.network.impl.UserDataSourceImpl
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

    @Binds
    fun bindsSearchDataSource(impl: SearchDataSourceImpl): SearchDataSource
}