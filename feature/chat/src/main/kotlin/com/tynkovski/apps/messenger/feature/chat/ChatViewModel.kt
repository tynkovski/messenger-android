package com.tynkovski.apps.messenger.feature.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.tynkovski.apps.messenger.core.data.repository.MessagesRepository
import com.tynkovski.apps.messenger.core.data.repository.RoomsRepository
import com.tynkovski.apps.messenger.core.data.repository.UsersRepository
import com.tynkovski.apps.messenger.core.model.Result
import com.tynkovski.apps.messenger.core.model.data.Room
import com.tynkovski.apps.messenger.core.model.data.User
import com.tynkovski.apps.messenger.core.ui.chat.ChatState
import com.tynkovski.apps.messenger.feature.chat.navigation.ChatArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
internal class ChatViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val messagesRepository: MessagesRepository,
    private val roomsRepository: RoomsRepository,
    private val usersRepository: UsersRepository,
) : ViewModel() {
    private val chatArgs: ChatArgs = ChatArgs(savedStateHandle)
    val myselfId = runBlocking { usersRepository.getUser().first().id }

    val roomState: StateFlow<ChatState> = roomsRepository
        .getRoom(chatArgs.chatId)
        .map<Room, ChatState> { room ->
            ChatState.Success(
                image = room.image,
                name = room.name,
                users = room.users.map { userId ->
                    val user = usersRepository.getUser(userId).first()
                    user.name ?: user.login
                }
            )
        }
        .catch { emit(ChatState.Error(it)) }
        .stateIn(
            scope = viewModelScope,
            initialValue = ChatState.Loading,
            started = SharingStarted.WhileSubscribed(),
        )

    val pager = messagesRepository.getPagingMessages(chatArgs.chatId).map { paging ->
        paging.map { message ->
            val sender = if (message.senderId != myselfId) usersRepository
                .getUser(message.senderId)
                .firstOrNull()
                ?.let { it.name ?: it.login } else null
            MessageUi.fromMessage(message, sender, myselfId)
        }
    }.cachedIn(viewModelScope)

    init {
        messagesRepository.startWebsocket()
    }
}