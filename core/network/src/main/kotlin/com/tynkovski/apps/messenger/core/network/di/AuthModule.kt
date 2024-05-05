package com.tynkovski.apps.messenger.core.network.di

import com.tynkovski.apps.messenger.core.network.AuthDataSource
import com.tynkovski.apps.messenger.core.network.impl.AuthDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface AuthModule {
    @Binds
    fun binds(impl: AuthDataSourceImpl): AuthDataSource
}