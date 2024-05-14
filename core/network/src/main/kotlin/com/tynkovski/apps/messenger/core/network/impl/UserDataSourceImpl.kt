package com.tynkovski.apps.messenger.core.network.impl

import androidx.tracing.trace
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tynkovski.apps.messenger.core.network.BuildConfig
import com.tynkovski.apps.messenger.core.network.UserDataSource
import com.tynkovski.apps.messenger.core.network.model.response.UserResponse
import com.tynkovski.apps.messenger.core.network.retrofit.UsersNetworkApi
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Inject

private const val BASE_URL = BuildConfig.BACKEND_URL

class UserDataSourceImpl @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : UserDataSource {
    private val api: UsersNetworkApi = trace("UsersNetworkApi") {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(UsersNetworkApi::class.java)
    }

    override suspend fun getUser(): UserResponse {
        return api.getUser()
    }

    override suspend fun getUser(id: Long): UserResponse {
        return api.getUserById(id)
    }

    override suspend fun editUser(
        name: String?,
        image: String?
    ): UserResponse {
        return api.editUser(UsersNetworkApi.UpdateUserRequest(name, image))
    }
}