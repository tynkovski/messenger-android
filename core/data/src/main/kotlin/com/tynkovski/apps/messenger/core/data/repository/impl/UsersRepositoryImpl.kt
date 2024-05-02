package com.tynkovski.apps.messenger.core.data.repository.impl

import com.tynkovski.apps.messenger.core.data.Synchronizer
import com.tynkovski.apps.messenger.core.data.repository.UsersRepository
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(

) : UsersRepository {
    override suspend fun syncWith(synchronizer: Synchronizer): Boolean = true // todo add sync
}