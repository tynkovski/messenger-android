package com.tynkovski.apps.messenger.core.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: Throwable) : Result<Nothing>
    data object Loading : Result<Nothing>
}

// deprecated
fun <T> Flow<T>.asResult(): Flow<Result<T>> = map<T, Result<T>> { Result.Success(it) }
    .onStart { emit(Result.Loading) }
    .catch { emit(Result.Error(it)) }

fun <R, L, E> Flow<NetResult<R, E>>.toResult(
    mapper: (R) -> L
): Flow<Result<L>> {
    return map { it.toLocalResult(mapper) }
        .onStart { emit(Result.Loading) }
}

private fun <R, L, E> NetResult<R, E>.toLocalResult(
    mapper: (R) -> L
): Result<L> = when (this) {
    is NetResult.Success -> Result.Success(mapper(value))
    is NetResult.Error -> throw ErrorException.NetError(code, message)
}

fun <T> Flow<Result<T>>.onLoading(
    action: suspend () -> Unit,
): Flow<Result<T>> = flow {
    collect { value ->
        if (value is Result.Loading) action()
        emit(value)
    }
}

fun <T> Flow<Result<T>>.onSuccess(
    action: suspend (value: T) -> Unit,
): Flow<Result<T>> = flow {
    collect { value ->
        if (value is Result.Success) action(value.data)
        emit(value)
    }
}

fun <T> Flow<Result<T>>.onError(
    action: suspend (value: Throwable) -> Unit,
): Flow<Result<T>> = catch {
    it.printStackTrace()
    action(it.toErrorException())
}

