package com.tynkovski.apps.messenger.core.model

sealed class ErrorException : Throwable() {
    data class NetError(val code: Int, override val message: String) : ErrorException()
    data object Unknown : ErrorException()
}

fun Throwable.toErrorException(): ErrorException = when (val error = this) {
    is ErrorException.NetError -> error
    else -> ErrorException.Unknown
}