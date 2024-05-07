package com.tynkovski.apps.messenger.core.network.impl

import androidx.tracing.trace
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tynkovski.apps.messenger.core.model.NetResult
import com.tynkovski.apps.messenger.core.network.AuthDataSource
import com.tynkovski.apps.messenger.core.network.BuildConfig
import com.tynkovski.apps.messenger.core.network.model.AccessResponse
import com.tynkovski.apps.messenger.core.network.model.ErrorResponse
import com.tynkovski.apps.messenger.core.network.model.TokenResponse
import com.tynkovski.apps.messenger.core.network.retrofit.AuthNetworkApi
import com.tynkovski.apps.messenger.core.network.util.asNetResult
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
    ): NetResult<TokenResponse, ErrorResponse> {
        return api.signUp(AuthNetworkApi.SignUpRequest(login, password, name, null)).asNetResult()
    }

    override suspend fun signIn(
        login: String,
        password: String
    ): NetResult<TokenResponse, ErrorResponse> {
        return api.signIn(AuthNetworkApi.SignInRequest(login, password)).asNetResult()
    }

    override suspend fun refreshToken(refreshToken: String): NetResult<AccessResponse, ErrorResponse> {
        return api.refreshToken(AuthNetworkApi.RefreshTokenRequest(refreshToken)).asNetResult()
    }

    override suspend fun logout(refreshToken: String): NetResult<Unit, ErrorResponse> {
        return api.logout(AuthNetworkApi.RefreshTokenRequest(refreshToken)).asNetResult()
    }
}