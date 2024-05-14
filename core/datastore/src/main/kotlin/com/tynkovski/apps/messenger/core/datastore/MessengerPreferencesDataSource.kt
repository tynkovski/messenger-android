package com.tynkovski.apps.messenger.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class MessengerPreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        const val DATA_STORE_NAME = "preferences"
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val USER_ID = longPreferencesKey("user_id")
    }

    data class TokenPreferences(
        val accessToken: String,
        val refreshToken: String
    )

    data class UserPreferences(
        val userId: Long
    )

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(DATA_STORE_NAME, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            getUserPreferences(preferences)
        }

    val tokenPreferencesFlow: Flow<TokenPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(DATA_STORE_NAME, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            getTokenPreferences(preferences)
        }

    suspend fun clear() {
        try {
            dataStore.edit {
                it.clear()
            }
        } catch (ioException: IOException) {
            Log.e(DATA_STORE_NAME, "Failed to update token preferences", ioException)
        }
    }

    suspend fun setAccessToken(accessToken: String) {
        try {
            dataStore.edit { preferences ->
                preferences[ACCESS_TOKEN] = accessToken
            }
        } catch (ioException: IOException) {
            Log.e(DATA_STORE_NAME, "Failed to update token preferences", ioException)
        }
    }

    suspend fun setRefreshToken(refreshToken: String) {
        try {
            dataStore.edit { preferences ->
                preferences[REFRESH_TOKEN] = refreshToken
            }
        } catch (ioException: IOException) {
            Log.e(DATA_STORE_NAME, "Failed to update token preferences", ioException)
        }
    }

    suspend fun setUserId(userId: Long) {
        try {
            dataStore.edit { preferences ->
                preferences[USER_ID] = userId
            }
        } catch (ioException: IOException) {
            Log.e(DATA_STORE_NAME, "Failed to update token preferences", ioException)
        }
    }

    private fun getTokenPreferences(preferences: Preferences): TokenPreferences {
        val accessToken = preferences[ACCESS_TOKEN] ?: ""
        val refreshToken = preferences[REFRESH_TOKEN] ?: ""
        return TokenPreferences(accessToken, refreshToken)
    }

    private fun getUserPreferences(preferences: Preferences): UserPreferences {
        val userId = preferences[USER_ID] ?: 0
        return UserPreferences(userId)
    }
}