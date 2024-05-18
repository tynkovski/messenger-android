package com.tynkovski.apps.messenger.core.network.retrofit

import com.tynkovski.apps.messenger.core.network.model.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchNetworkApi {
    @GET("/search/user")
    suspend fun getUser(@Query("login") login: String): UserResponse
}