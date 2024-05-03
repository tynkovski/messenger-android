package com.tynkovski.apps.messenger.sync.di

import com.tynkovski.apps.messenger.core.data.util.SyncManager
import com.tynkovski.apps.messenger.sync.status.StubSyncSubscriber
import com.tynkovski.apps.messenger.sync.status.SyncSubscriber
import com.tynkovski.apps.messenger.sync.status.WorkManagerSyncManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SyncModule {
    @Binds
    internal abstract fun bindsSyncStatusMonitor(
        syncStatusMonitor: WorkManagerSyncManager
    ): SyncManager

    @Binds
    internal abstract fun bindsSyncSubscriber(
        syncSubscriber: StubSyncSubscriber
    ): SyncSubscriber
}