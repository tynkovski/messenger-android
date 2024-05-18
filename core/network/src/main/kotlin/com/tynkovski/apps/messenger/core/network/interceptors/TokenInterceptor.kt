package com.tynkovski.apps.messenger.core.network.interceptors

import android.util.Log
import com.tynkovski.apps.messenger.core.datastore.TokenHolder
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val tokenHolder: TokenHolder
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenHolder.getToken()
        val request= if (token == null) {
            chain.request()
        } else {
            Log.d("JWT.Access", token.accessToken)
            val authenticatedRequest = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer ${token.accessToken}")
                .build()
            authenticatedRequest
        }
        return chain.proceed(request)
    }
}