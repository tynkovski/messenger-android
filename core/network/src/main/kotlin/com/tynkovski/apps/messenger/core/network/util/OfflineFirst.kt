package com.tynkovski.apps.messenger.core.network.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

inline fun <Entry, Response, Entity> offlineFirst(
    crossinline getEntity: suspend () -> Entity?,
    crossinline getResponse: suspend () -> Response,
    crossinline saveToDatabase: suspend (Entity) -> Unit,
    crossinline localToEntryMapper: suspend (Entity) -> Entry,
    crossinline remoteToEntryMapper: suspend (Response) -> Entry,
    crossinline entryToLocalMapper: suspend (Entry) -> Entity,
): Flow<Entry> = flow {
    val localData = getEntity()
    if (localData != null) {
        emit(localToEntryMapper(localData))
    }

    val remoteData = getResponse()

    val entry = remoteToEntryMapper(remoteData)

    val newLocal = entryToLocalMapper(entry)

    if (localData != newLocal) {
        saveToDatabase(newLocal)
    }

    emit(entry)
}