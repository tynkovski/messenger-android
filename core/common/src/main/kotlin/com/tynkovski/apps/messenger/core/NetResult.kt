package com.tynkovski.apps.messenger.core

sealed interface NetResult<out R, out E> {
    data class Error<E>(val code: Int, val message: String) : NetResult<Nothing, E>
    data class Success<R>(val value: R) : NetResult<R, Nothing>
}

