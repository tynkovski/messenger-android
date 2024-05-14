package com.tynkovski.apps.messenger.core.datastore

interface UserHolder {
    suspend fun setUserId(userId: Long)
    fun getUserId(): Long
}