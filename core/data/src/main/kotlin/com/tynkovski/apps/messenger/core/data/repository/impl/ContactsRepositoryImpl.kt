package com.tynkovski.apps.messenger.core.data.repository.impl

import com.tynkovski.apps.messenger.core.data.repository.ContactsRepository
import com.tynkovski.apps.messenger.core.data.util.UserMapper
import com.tynkovski.apps.messenger.core.data.util.simpleMessageMapper
import com.tynkovski.apps.messenger.core.model.data.User
import com.tynkovski.apps.messenger.core.network.ContactsDataSource
import com.tynkovski.apps.messenger.core.network.Dispatcher
import com.tynkovski.apps.messenger.core.network.MessengerDispatchers
import com.tynkovski.apps.messenger.core.network.UserDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ContactsRepositoryImpl @Inject constructor(
    private val contactsNetwork: ContactsDataSource,
    @Dispatcher(MessengerDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : ContactsRepository {
    override fun getContacts(): Flow<List<User>> = flow {
        emit(contactsNetwork.getContacts())
    }.map(UserMapper.responseListToEntryList).flowOn(ioDispatcher)

    override fun addContact(userId: Long): Flow<List<User>> = flow {
        emit(contactsNetwork.addContact(userId))
    }.map(UserMapper.responseListToEntryList).flowOn(ioDispatcher)

    override fun removeContact(userId: Long): Flow<List<User>> = flow {
        emit(contactsNetwork.removeContact(userId))
    }.map(UserMapper.responseListToEntryList).flowOn(ioDispatcher)
}