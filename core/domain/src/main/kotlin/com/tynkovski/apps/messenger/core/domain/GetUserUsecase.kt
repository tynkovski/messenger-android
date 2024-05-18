package com.tynkovski.apps.messenger.core.domain

import com.tynkovski.apps.messenger.core.data.repository.UsersRepository
import javax.inject.Inject

class GetUserUsecase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    operator fun invoke(id: Long) = usersRepository.getUser(id)
}