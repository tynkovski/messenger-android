package com.tynkovski.apps.messenger.core.domain

import com.tynkovski.apps.messenger.core.data.repository.SearchRepository
import javax.inject.Inject

class FindUserUsecase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    operator fun invoke(login: String) = searchRepository.getUser(login)
}