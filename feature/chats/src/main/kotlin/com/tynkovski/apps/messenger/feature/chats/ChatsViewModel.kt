package com.tynkovski.apps.messenger.feature.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tynkovski.apps.messenger.core.data.repository.MessagesRepository
import com.tynkovski.apps.messenger.core.data.repository.RoomsRepository
import com.tynkovski.apps.messenger.core.data.repository.UsersRepository
import com.tynkovski.apps.messenger.core.data.websockets.RoomsWebsocketClient
import com.tynkovski.apps.messenger.core.model.Result
import com.tynkovski.apps.messenger.core.model.data.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val messagesRepository: MessagesRepository,
    private val usersRepository: UsersRepository,
    private val roomsRepository: RoomsRepository,
    private val roomsWebsocketClient: RoomsWebsocketClient,
) : ViewModel() {
    init {
        roomsWebsocketClient.start()
    }

    val rooms: StateFlow<Result<List<RoomUi>>> = roomsRepository
        .observeRooms()
        .map<List<Room>, Result<List<RoomUi>>> { list ->
            val uiItems = list.map {
                val user = usersRepository.getUser(it.lastAction.authorId).first()
                val senderName = user.name ?: user.login
                val unread = 0
                RoomUi.fromRoom(it, senderName, unread)
            }
            Result.Success(uiItems)
        }
        .catch { Result.Error(it) }
        .stateIn(
            scope = viewModelScope,
            initialValue = Result.Loading,
            started = SharingStarted.WhileSubscribed(5_000),
        )

    override fun onCleared() {
        roomsWebsocketClient.stop()
        super.onCleared()
    }
}