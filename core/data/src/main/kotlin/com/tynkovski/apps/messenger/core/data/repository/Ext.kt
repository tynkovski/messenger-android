package com.tynkovski.apps.messenger.core.data.repository

import com.tynkovski.apps.messenger.core.ErrorException
import com.tynkovski.apps.messenger.core.NetResult
import com.tynkovski.apps.messenger.core.Result

fun <R, L, E> NetResult<R, E>.toResult(
    mapper: (R) -> L
): Result<L> = when (this) {
    is NetResult.Success -> Result.Success(mapper(value))
    is NetResult.Error -> throw ErrorException.NetError(code, message)
}
