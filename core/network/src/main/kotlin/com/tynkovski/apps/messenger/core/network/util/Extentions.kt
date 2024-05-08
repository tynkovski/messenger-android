package com.tynkovski.apps.messenger.core.network.util

import com.tynkovski.apps.messenger.core.model.NetResult
import retrofit2.Response

fun<R, E> Response<R>.asNetResult(): NetResult<R, E> {
    return if (isSuccessful) {
        NetResult.Success(body()!!)
    } else {
        NetResult.Error(code(), "${errorBody()}")
    }
}