@file:OptIn(ExperimentalPagingApi::class)

package com.tynkovski.apps.messenger.core.data.repository.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tynkovski.apps.messenger.core.data.Synchronizer
import com.tynkovski.apps.messenger.core.data.paging.MessagesRemoteMediator
import com.tynkovski.apps.messenger.core.data.repository.MessagesRepository
import com.tynkovski.apps.messenger.core.data.util.MessageMapper
import com.tynkovski.apps.messenger.core.data.websockets.ChatsWebsocketsClient
import com.tynkovski.apps.messenger.core.database.MessengerDatabase
import com.tynkovski.apps.messenger.core.database.dao.MessagesDao
import com.tynkovski.apps.messenger.core.model.data.Message
import com.tynkovski.apps.messenger.core.network.Dispatcher
import com.tynkovski.apps.messenger.core.network.MessagesDataSource
import com.tynkovski.apps.messenger.core.network.MessengerDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class MessagesRepositoryImpl @Inject constructor(
    private val network: MessagesDataSource,
    private val dao: MessagesDao,
    private val db: MessengerDatabase,
    private val messagesWebsocketClient: ChatsWebsocketsClient,
    @Dispatcher(MessengerDispatchers.IO) private val dispatcher: CoroutineDispatcher,
) : MessagesRepository {

    override val isConnected = messagesWebsocketClient.isConnected

    override fun getPagingMessages(roomId: Long): Flow<PagingData<Message>> =
        Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = MessagesRemoteMediator(
                roomId = roomId,
                db = db,
                dao = dao,
                network = network
            ),
            pagingSourceFactory = { dao.pagingSource(roomId) }
        ).flow.map(MessageMapper.localPagerToEntryPager)

    override fun sendMessage(roomId: Long, message: String): Flow<Boolean> = flow {
        emit(messagesWebsocketClient.sendMessage(roomId, message))
    }.flowOn(dispatcher)

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean = true // todo add sync
}