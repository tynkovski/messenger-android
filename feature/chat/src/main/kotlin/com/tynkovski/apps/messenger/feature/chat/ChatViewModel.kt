package com.tynkovski.apps.messenger.feature.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tynkovski.apps.messenger.feature.chat.navigation.ChatArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val chatArgs: ChatArgs = ChatArgs(savedStateHandle)

    val chatId = chatArgs.chatId
}