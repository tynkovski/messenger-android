package com.tynkovski.apps.messenger.core.data

interface Synchronizer {
    // suspend fun getChangeListVersions(): ChangeListVersions
    // suspend fun updateChangeListVersions(update: ChangeListVersions.() -> ChangeListVersions)
    suspend fun Syncable.sync() = this@sync.syncWith(this@Synchronizer)
}

interface Syncable {
    suspend fun syncWith(synchronizer: Synchronizer): Boolean
}
