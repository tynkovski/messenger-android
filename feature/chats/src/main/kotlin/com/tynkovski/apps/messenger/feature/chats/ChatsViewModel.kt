package com.tynkovski.apps.messenger.feature.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tynkovski.apps.messenger.core.data.repository.RoomsRepository
import com.tynkovski.apps.messenger.core.data.websockets.RoomsWebsocketClient
import com.tynkovski.apps.messenger.core.model.Result
import com.tynkovski.apps.messenger.core.model.data.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val roomsRepository: RoomsRepository,
    private val roomsWebsocketClient: RoomsWebsocketClient,
) : ViewModel() {
    init {
        roomsWebsocketClient.start()
    }

    val rooms: StateFlow<Result<List<Room>>> = roomsRepository
        .observeRooms()
        .map<List<Room>, Result<List<Room>>> { Result.Success(it) }
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