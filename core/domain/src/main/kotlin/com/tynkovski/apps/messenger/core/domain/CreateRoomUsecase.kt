package com.tynkovski.apps.messenger.core.domain

import com.tynkovski.apps.messenger.core.data.repository.RoomsRepository
import javax.inject.Inject

class CreateChatUsecase @Inject constructor(
    private val roomsRepository: RoomsRepository
) {
    operator fun invoke(
        collocutorId: Long,
        name: String? = null,
        image: String? = null
    ) = roomsRepository.createRoom(collocutorId, name, image)
}