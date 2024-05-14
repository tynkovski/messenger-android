package com.tynkovski.apps.messenger.core.network.retrofit

import com.tynkovski.apps.messenger.core.network.model.response.AccessResponse
import com.tynkovski.apps.messenger.core.network.model.response.TokenResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.POST

private const val AUTH_URL = "/auth"

interface AuthNetworkApi {
    @Serializable
    data class SignUpRequest(
        @SerialName("login") val login: String,
        @SerialName("password") val password: String,
        @SerialName("image") val image: String?,
        @SerialName("name") val name: String?
    )

    @Serializable
    data class SignInRequest(
        val login: String,
        val password: String
    )

    @Serializable
    data class RefreshTokenRequest(
        val refreshToken: String
    )

    @POST("$AUTH_URL/signUp")
    suspend fun signUp(@Body request: SignUpRequest): TokenResponse

    @POST("$AUTH_URL/signIn")
    suspend fun signIn(@Body request: SignInRequest): TokenResponse

    @POST("$AUTH_URL/refreshToken")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): AccessResponse

    @POST("$AUTH_URL/logout")
    suspend fun logout(@Body request: RefreshTokenRequest): Unit
}
