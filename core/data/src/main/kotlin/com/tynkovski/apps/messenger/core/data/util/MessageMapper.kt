package com.tynkovski.apps.messenger.core.data.util

import androidx.paging.PagingData
import androidx.paging.map
import com.tynkovski.apps.messenger.core.database.model.MessageEntity
import com.tynkovski.apps.messenger.core.database.model.RoomEntity
import com.tynkovski.apps.messenger.core.model.data.Message
import com.tynkovski.apps.messenger.core.model.data.Room
import com.tynkovski.apps.messenger.core.network.model.response.MessageResponse
import java.time.LocalDateTime

object MessageMapper {
    private fun setMapper(string: String) = if (string.isEmpty()) {
        setOf()
    } else {
        string.split(",").map { it.toLong() }.toSet()
    }

    fun localToEntry(message: MessageEntity) = with(message) {
        Message(
            id = id,
            senderId = senderId,
            roomId = roomId,
            text = text,
            readBy = setMapper(readBy),
            isDeleted = isDeleted,
            editedAt = editedAt,
            sentAt = sentAt
        )
    }

    val localPagerToEntryPager: (PagingData<MessageEntity>) -> PagingData<Message> = { messages ->
        messages.map(MessageMapper::localToEntry)
    }

    fun localListToEntryList(messages: List<MessageEntity>) = messages.map(::localToEntry)

    fun entryToLocal(message: Message) = with(message) {
        MessageEntity(
            id = id,
            senderId = senderId,
            roomId = roomId,
            text = text,
            readBy = readBy.joinToString(","),
            isDeleted = isDeleted,
            editedAt = editedAt,
            sentAt = sentAt
        )
    }

    fun entryListToLocalList(messages: List<Message>) = messages.map(::entryToLocal)

    fun remoteToEntry(message: MessageResponse) = with(message) {
        Message(
            id = id,
            senderId = senderId,
            roomId = roomId,
            text = text,
            readBy = readBy.toSet(),
            isDeleted = isDeleted,
            editedAt = editedAt?.let { LocalDateTime.parse(it, timeFormatter) },
            sentAt = LocalDateTime.parse(sentAt, timeFormatter)
        )
    }

    fun remoteListToEntryList(messages: List<MessageResponse>) = messages.map(::remoteToEntry)

    fun entryToRemote(message: Message) = with(message) {
        MessageResponse(
            id = id,
            senderId = senderId,
            roomId = roomId,
            text = text,
            readBy = readBy.toList(),
            editedAt = timeFormatter.format(editedAt),
            sentAt = timeFormatter.format(sentAt),
            isDeleted = isDeleted
        )
    }
}