package com.tynkovski.apps.messenger.feature.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.tynkovski.apps.messenger.core.data.repository.RoomsRepository
import com.tynkovski.apps.messenger.core.data.websockets.RoomsWebsocketClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val roomsRepository: RoomsRepository,
) : ViewModel() {

    val isConnected = roomsRepository.isConnected

    val pager = roomsRepository.getPagingRooms().map { paging ->
        paging.map { room -> RoomUi.fromRoom(room, room.lastAction.authorName, 0) }
    }.cachedIn(viewModelScope)

    init {
        roomsRepository.startWebsocket()
    }
}