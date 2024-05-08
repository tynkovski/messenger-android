package com.tynkovski.apps.messenger.core.data.repository.impl

import com.tynkovski.apps.messenger.core.data.Synchronizer
import com.tynkovski.apps.messenger.core.data.repository.MessagesRepository
import javax.inject.Inject

internal class MessagesRepositoryImpl @Inject constructor(

) : MessagesRepository {
    override suspend fun syncWith(synchronizer: Synchronizer): Boolean = true // todo add sync
}