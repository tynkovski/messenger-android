package com.tynkovski.apps.messenger.core.datastore.impl

import com.tynkovski.apps.messenger.core.datastore.MessengerPreferencesDataSource
import com.tynkovski.apps.messenger.core.datastore.UserHolder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class UserHolderImpl @Inject constructor(
    private val messengerPreferencesDataSource: MessengerPreferencesDataSource,
) : UserHolder {
    override suspend fun setUserId(userId: Long) {
        messengerPreferencesDataSource.setUserId(userId)
    }

    override fun getUserId() = runBlocking {
        messengerPreferencesDataSource
            .userPreferencesFlow
            .map { it.userId }
            .first()
    }
}