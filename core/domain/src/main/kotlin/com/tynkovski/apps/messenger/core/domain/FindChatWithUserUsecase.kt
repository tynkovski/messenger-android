package com.tynkovski.apps.messenger.core.domain

import com.tynkovski.apps.messenger.core.data.repository.RoomsRepository
import javax.inject.Inject

class FindChatWithUserUsecase @Inject constructor(
    private val roomsRepository: RoomsRepository
) {
    operator fun invoke(collocutorId: Long) = roomsRepository.findRoom(collocutorId)
}