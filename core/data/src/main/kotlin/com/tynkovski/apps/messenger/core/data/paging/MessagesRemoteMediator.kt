@file:OptIn(ExperimentalPagingApi::class)

package com.tynkovski.apps.messenger.core.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.tynkovski.apps.messenger.core.data.util.MessageMapper
import com.tynkovski.apps.messenger.core.database.MessengerDatabase
import com.tynkovski.apps.messenger.core.database.dao.MessagesDao
import com.tynkovski.apps.messenger.core.database.model.MessageEntity
import com.tynkovski.apps.messenger.core.network.MessagesDataSource

class MessagesRemoteMediator(
    private val roomId: Long,
    private val db: MessengerDatabase,
    private val dao: MessagesDao,
    private val network: MessagesDataSource
) : RemoteMediator<Int, MessageEntity>() {
    private var currentPage = 0L

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MessageEntity>
    ): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> 0L
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        0L
                    } else {
                        currentPage + 1
                    }
                }
            }
            val response = network.getMessagesPaged(roomId, page, state.config.pageSize)
            val messages = MessageMapper.remoteListToEntryList(response.messages)
            val entities = MessageMapper.entryListToLocalList(messages)

            currentPage = page

            db.withTransaction {
                dao.upsert(entities)
            }

            return MediatorResult.Success(endOfPaginationReached = messages.isEmpty())
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }
}