package com.tynkovski.apps.messenger.core.network.interceptors

import android.util.Log
import com.tynkovski.apps.messenger.core.datastore.TokenHolder
import com.tynkovski.apps.messenger.core.model.NetResult
import com.tynkovski.apps.messenger.core.network.AuthDataSource
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RefreshTokenInterceptor @Inject constructor(
    private val tokenHolder: TokenHolder,
    private val authDataSource: AuthDataSource,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        when (response.code) {
            401 -> synchronized(this) {
                response.close()
                val newToken = refreshToken()
                if (newToken.isNullOrBlank()) {
                    logout()
                    return@synchronized
                } else {
                    setToken(newToken)
                }
                return newRequest(chain, newToken)
            }
        }
        return response
    }

    private fun refreshToken(): String? = runBlocking {
        tokenHolder.getToken()?.refreshToken?.let {
            val response = authDataSource.refreshToken(it)
            val accessResponse = (response as? NetResult.Success)?.value
            accessResponse?.accessToken
        }
    }

    private fun newRequest(chain: Interceptor.Chain, newToken: String): Response {
        Log.d("JWT.Access", newToken)
        val newRequest = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $newToken")
            .build()
        return chain.proceed(newRequest)
    }

    private fun setToken(newToken: String) = runBlocking {
        tokenHolder.setAccessToken(newToken)
    }

    private fun logout() = runBlocking {
        tokenHolder.logout()
    }
}