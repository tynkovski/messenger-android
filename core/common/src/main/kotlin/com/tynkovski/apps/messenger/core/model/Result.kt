package com.tynkovski.apps.messenger.core.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: Throwable) : Result<Nothing>
    data object Loading : Result<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> = map<T, Result<T>> { Result.Success(it) }
    .onStart { emit(Result.Loading) }
    .catch { emit(Result.Error(it)) }

suspend fun <T> Flow<T>.collector(
    onLoading: suspend () -> Unit,
    onSuccess: suspend (value: T) -> Unit,
    onError: suspend (value: Throwable) -> Unit,
) = asResult()
    .onEach {
        when (it) {
            is Result.Error -> onError(it.exception)
            Result.Loading -> onLoading()
            is Result.Success -> onSuccess(it.data)
        }
    }.collect()
