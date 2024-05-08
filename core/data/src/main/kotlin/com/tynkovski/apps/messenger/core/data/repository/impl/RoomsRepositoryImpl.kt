package com.tynkovski.apps.messenger.core.data.repository.impl

import com.tynkovski.apps.messenger.core.data.Synchronizer
import com.tynkovski.apps.messenger.core.data.repository.RoomsRepository
import javax.inject.Inject

internal class RoomsRepositoryImpl @Inject constructor(

) : RoomsRepository {
    override suspend fun syncWith(synchronizer: Synchronizer): Boolean = true // todo add sync
}