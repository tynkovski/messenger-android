package com.tynkovski.apps.messenger.core.data.repository.impl

import com.tynkovski.apps.messenger.core.data.Synchronizer
import com.tynkovski.apps.messenger.core.data.repository.MessagesRepository
import com.tynkovski.apps.messenger.core.network.MessagesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class MessagesRepositoryImpl @Inject constructor(
    private val network: MessagesDataSource,
) : MessagesRepository {
    override fun getUnreadMessagesCount(roomId: Long): Flow<Int> = flow {
        emit(network.getUnreadMessagesCount(roomId))
    }.map { it.count }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean = true // todo add sync
}