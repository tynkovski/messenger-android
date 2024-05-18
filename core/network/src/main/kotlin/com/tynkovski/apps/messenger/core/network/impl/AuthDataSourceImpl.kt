package com.tynkovski.apps.messenger.core.network.impl

import androidx.tracing.trace
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tynkovski.apps.messenger.core.network.AuthDataSource
import com.tynkovski.apps.messenger.core.network.BuildConfig
import com.tynkovski.apps.messenger.core.network.model.response.AccessResponse
import com.tynkovski.apps.messenger.core.network.model.response.TokenResponse
import com.tynkovski.apps.messenger.core.network.retrofit.AuthNetworkApi
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Inject

private const val BASE_URL = BuildConfig.BACKEND_URL

class AuthDataSourceImpl @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : AuthDataSource {

    private val api: AuthNetworkApi = trace("AuthNetworkApi") {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(AuthNetworkApi::class.java)
    }

    override suspend fun signUp(
        name: String?,
        login: String,
        password: String
    ): TokenResponse {
        return api.signUp(AuthNetworkApi.SignUpRequest(login, password, name, null))
    }

    override suspend fun signIn(
        login: String,
        password: String
    ): TokenResponse {
        return api.signIn(AuthNetworkApi.SignInRequest(login, password))
    }

    override suspend fun refreshToken(refreshToken: String): AccessResponse {
        return api.refreshToken(AuthNetworkApi.RefreshTokenRequest(refreshToken))
    }

    override suspend fun logout(refreshToken: String): Unit {
        return api.logout(AuthNetworkApi.RefreshTokenRequest(refreshToken))
    }
}