package com.tynkovski.apps.messenger.feature.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.tynkovski.apps.messenger.core.data.repository.MessagesRepository
import com.tynkovski.apps.messenger.core.data.repository.RoomsRepository
import com.tynkovski.apps.messenger.core.data.repository.UsersRepository
import com.tynkovski.apps.messenger.core.model.data.Room
import com.tynkovski.apps.messenger.core.model.data.User
import com.tynkovski.apps.messenger.core.ui.chat.ChatState
import com.tynkovski.apps.messenger.core.ui.chat.MessageUi
import com.tynkovski.apps.messenger.feature.chat.navigation.ChatArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val INPUT_STATE = "input_state"

@HiltViewModel
internal class ChatViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val messagesRepository: MessagesRepository,
    private val roomsRepository: RoomsRepository,
    private val usersRepository: UsersRepository,
) : ViewModel() {
    private val mSideEffect = MutableSharedFlow<ChatSideEffect>()

    val inputState = savedStateHandle.getStateFlow(INPUT_STATE, "")

    fun inputStateChanged(value: String) {
        savedStateHandle[INPUT_STATE] = value
    }

    private val chatArgs: ChatArgs = ChatArgs(savedStateHandle)

    val state: StateFlow<ChatState> = combine(
        roomsRepository.getRoom(chatArgs.chatId),
        usersRepository.getUser(),
        ::Pair
    ).map<Pair<Room, User>, ChatState> { (room, myself) ->
        ChatState.Success(
            image = room.image,
            name = room.name,
            users = room.users.map { userId ->
                val user = usersRepository.getUser(userId).first()
                user.name ?: user.login
            },
            paging = messagesRepository.getPagingMessages(chatArgs.chatId).map { paging ->
                paging.map { message ->

                    val senderName = if (message.senderId == myself.id) {
                        null
                    } else {
                        val user = usersRepository.getUser(message.senderId).firstOrNull()
                        user?.name ?: user?.login
                    }

                    MessageUi.fromMessage(message, senderName, myself.id)
                }
            }.cachedIn(viewModelScope)
        )
    }.catch { emit(ChatState.Error(it)) }.stateIn(
        scope = viewModelScope,
        initialValue = ChatState.Loading,
        started = SharingStarted.WhileSubscribed(),
    )

    fun sendMessage() {
        viewModelScope.launch {
            messagesRepository.sendMessage(chatArgs.chatId, inputState.value)
            inputStateChanged("")
        }
    }

    val sideEffect = mSideEffect.asSharedFlow()
}