package com.tynkovski.apps.messenger.feature.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.tynkovski.apps.messenger.core.data.repository.RoomsRepository
import com.tynkovski.apps.messenger.core.data.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val roomsRepository: RoomsRepository,
) : ViewModel() {

    val isConnected = roomsRepository.isConnected

    val pager = roomsRepository.getPagingRooms().map { paging ->
        paging.map { room ->
            val users = room.users.map { userId ->
                val user = usersRepository.getUser(userId).first()
                user.name ?: user.login
            }
            RoomUi.fromRoom(room, users, room.lastAction.authorName, 0)
        }
    }.cachedIn(viewModelScope)

    init {
        roomsRepository.startWebsocket()
    }
}