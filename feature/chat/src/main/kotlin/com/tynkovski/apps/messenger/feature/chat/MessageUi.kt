package com.tynkovski.apps.messenger.feature.chat

import androidx.compose.runtime.Stable
import com.tynkovski.apps.messenger.core.model.data.Message
import java.time.format.DateTimeFormatter

@Stable
data class MessageUi(
    val id: Long,
    val owner: Boolean,
    val senderId: Long,
    val senderName: String?,
    val text: String,
    val read: Boolean,
    val isDeleted: Boolean,
    val editedAt: String?,
    val sentAt: String
) {
    companion object {
        private val messageTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

        fun fromMessage(
            message: Message,
            senderName: String?,
            myselfId: Long
        ): MessageUi = with(message) {
            MessageUi(
                id = id,
                owner = senderId == myselfId,
                senderId = senderId,
                senderName = senderName,
                text = text,
                read = readBy.contains(myselfId),
                isDeleted = isDeleted,
                editedAt = editedAt?.let { messageTimeFormatter.format(it) },
                sentAt = messageTimeFormatter.format(sentAt)
            )
        }
    }
}