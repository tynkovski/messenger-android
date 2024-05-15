package com.tynkovski.apps.messenger.core.data.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tynkovski.apps.messenger.core.data.paging.RoomsRemoteMediator
import com.tynkovski.apps.messenger.core.database.MessengerDatabase
import com.tynkovski.apps.messenger.core.database.dao.RoomsDao
import com.tynkovski.apps.messenger.core.database.model.RoomEntity
import com.tynkovski.apps.messenger.core.network.RoomsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object PagingModule {
    @Provides
    @Singleton
    fun provideRoomsPager(
        db: MessengerDatabase,
        dao: RoomsDao,
        network: RoomsDataSource
    ): Pager<Int, RoomEntity> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = RoomsRemoteMediator(db, dao, network),
            pagingSourceFactory = { dao.pagingSource() }
        )
    }
}