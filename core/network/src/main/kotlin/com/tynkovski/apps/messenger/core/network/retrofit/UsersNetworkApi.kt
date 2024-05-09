package com.tynkovski.apps.messenger.core.network.retrofit

import com.tynkovski.apps.messenger.core.network.model.UserResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

private const val USERS_URL = "/user"

interface UsersNetworkApi {
    @Serializable
    data class UpdateUserRequest(
        @SerialName("name") val name: String? = null,
        @SerialName("image") val image: String? = null
    )

    @GET(USERS_URL)
    suspend fun getUser(): UserResponse

    @GET("$USERS_URL/{id}")
    suspend fun getUserById(@Path(value = "id", encoded = true) id: Long): UserResponse

    @POST("$USERS_URL/edit")
    suspend fun editUser(@Body request: UpdateUserRequest): UserResponse
}