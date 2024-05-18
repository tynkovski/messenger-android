package com.tynkovski.apps.messenger.feature.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.tynkovski.apps.messenger.core.data.repository.MessagesRepository
import com.tynkovski.apps.messenger.core.data.repository.UsersRepository
import com.tynkovski.apps.messenger.feature.chat.navigation.ChatArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
internal class ChatViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val messagesRepository: MessagesRepository,
    private val usersRepository: UsersRepository,
) : ViewModel() {
    val myselfId = runBlocking { usersRepository.getUser().first().id }
    val chatArgs: ChatArgs = ChatArgs(savedStateHandle)

    val isConnected = messagesRepository.isConnected

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