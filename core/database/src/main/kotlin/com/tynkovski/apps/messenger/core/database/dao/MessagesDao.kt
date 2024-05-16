package com.tynkovski.apps.messenger.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.tynkovski.apps.messenger.core.database.model.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessagesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: MessageEntity)

    @Upsert
    suspend fun upsert(entity: MessageEntity)

    @Upsert
    suspend fun upsert(entity: List<MessageEntity>)

    @Delete
    suspend fun delete(entity: MessageEntity)

    @Query("SELECT * FROM messages WHERE isDeleted = 0 ORDER by sentAt DESC")
    fun getRooms(): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE isDeleted = 0 AND roomId = :roomId ORDER by sentAt DESC")
    fun pagingSource(roomId: Long): PagingSource<Int, MessageEntity>

}