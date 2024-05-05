package com.tynkovski.apps.messenger.core.network.retrofit

import com.tynkovski.apps.messenger.core.network.model.AccessResponse
import com.tynkovski.apps.messenger.core.network.model.TokenResponse
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

private const val AUTH_URL = "/auth"

interface AuthNetworkApi {
    @Serializable
    data class SignUpRequest(val name: String?, val login: String, val password: String)

    @Serializable
    data class SignInRequest(val login: String, val password: String)

    @Serializable
    data class RefreshTokenRequest(val refreshToken: String)

    @POST("${AUTH_URL}/signUp")
    suspend fun signUp(@Body request: SignUpRequest): Response<TokenResponse>

    @POST("${AUTH_URL}/signIn")
    suspend fun signIn(@Body request: SignInRequest): Response<TokenResponse>

    @POST("${AUTH_URL}/refreshToken")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<AccessResponse>

    @POST("${AUTH_URL}/logout")
    suspend fun logout(@Body request: RefreshTokenRequest): Response<Unit>
}
