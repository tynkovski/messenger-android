package com.tynkovski.apps.messenger.core.domain

import com.tynkovski.apps.messenger.core.data.repository.ContactsRepository
import javax.inject.Inject

class AddUserToContactsUsecase @Inject constructor(
    private val contactsRepository: ContactsRepository
) {
    operator fun invoke(userId: Long) = contactsRepository.addContact(userId)
}