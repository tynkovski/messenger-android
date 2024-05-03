package com.tynkovski.apps.messenger.sync.status

import android.util.Log
import javax.inject.Inject

private const val TAG = "StubSyncSubscriber"

internal class StubSyncSubscriber @Inject constructor() : SyncSubscriber {
    override suspend fun subscribe() {
        Log.d(TAG, "Subscribing to sync")
    }
}