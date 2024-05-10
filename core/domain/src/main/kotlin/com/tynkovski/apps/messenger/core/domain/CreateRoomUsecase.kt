package com.tynkovski.apps.messenger.core.domain

import com.tynkovski.apps.messenger.core.data.repository.RoomsRepository
import javax.inject.Inject

class CreateChatUsecase @Inject constructor(
    val roomsRepository: RoomsRepository
) {
    operator fun invoke(collocutorId: Long) = roomsRepository.createRoom(collocutorId, "New Chat")
}