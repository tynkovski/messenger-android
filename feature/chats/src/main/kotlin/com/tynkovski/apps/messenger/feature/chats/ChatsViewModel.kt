package com.tynkovski.apps.messenger.feature.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.tynkovski.apps.messenger.core.data.repository.ContactsRepository
import com.tynkovski.apps.messenger.core.data.repository.RoomsRepository
import com.tynkovski.apps.messenger.core.data.repository.UsersRepository
import com.tynkovski.apps.messenger.core.model.collector
import com.tynkovski.apps.messenger.core.model.data.User
import com.tynkovski.apps.messenger.core.ui.contact.ContactUi
import com.tynkovski.apps.messenger.core.ui.contact.ContactsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val roomsRepository: RoomsRepository,
    private val contactsRepository: ContactsRepository,
) : ViewModel() {
    private val mSideEffect = MutableSharedFlow<ChatsSideEffect>()

    val isConnected = roomsRepository.isConnected
    val sideEffect = mSideEffect.asSharedFlow()

    private val mSelectedChats = MutableStateFlow(setOf<Long>())
    val selectedChats = mSelectedChats.asStateFlow()

    fun selectChat(roomId: Long) {
        viewModelScope.launch {
            if (roomId in mSelectedChats.value) {
                mSelectedChats.emit(mSelectedChats.value - roomId)
            } else {
                mSelectedChats.emit(mSelectedChats.value + roomId)
            }
        }
    }

    fun deleteSelectedChats() {
        viewModelScope.launch {
            selectedChats.value.forEach { roomId ->
                roomsRepository.deleteRoom(roomId)
            }
            mSelectedChats.emit(setOf())
        }
    }

    fun clearSelectedChats() {
        viewModelScope.launch {
            mSelectedChats.emit(setOf())
        }
    }

    val contactsState = contactsRepository
        .getContacts()
        .map<List<User>, ContactsUiState> { contacts ->
            ContactsUiState.Success(contacts.map(ContactUi::fromUser))
        }
        .catch { emit(ContactsUiState.Error(it)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ContactsUiState.Loading,
        )

    val pager = roomsRepository.getPagingRooms().map { paging ->
        paging.map { room ->
            val users = room.users.map { userId ->
                val user = usersRepository.getUser(userId).first()
                user.name ?: user.login
            }
            RoomUi.fromRoom(room, users, room.lastAction.authorName, 0)
        }
    }.cachedIn(viewModelScope)

    fun findChatWithUser(userId: Long) {
        viewModelScope.launch {
            roomsRepository
                .findRoom(userId)
                .collector(
                    onSuccess = {
                        mSideEffect.emit(ChatsSideEffect.NavigateToChat(it.id))
                    }
                )
        }
    }

    init {
        roomsRepository.startWebsocket()
    }
}