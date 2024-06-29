package com.tynkovski.apps.messenger.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.tynkovski.apps.messenger.core.database.model.RoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: RoomEntity)

    @Upsert
    suspend fun upsert(entity: RoomEntity)

    @Upsert
    suspend fun upsert(entities: List<RoomEntity>)

    @Delete
    suspend fun delete(entity: RoomEntity)

    @Delete
    suspend fun delete(entities: List<RoomEntity>)

    @Query("DELETE FROM rooms")
    suspend fun clearAll()

    @Query("SELECT * FROM rooms WHERE isDeleted = 0 ORDER by actionDateTime DESC")
    fun getRooms(): Flow<List<RoomEntity>>

    @Query("SELECT * FROM rooms WHERE isDeleted = 0 ORDER by actionDateTime DESC")
    fun pagingSource(): PagingSource<Int, RoomEntity>

    @Query("SELECT * FROM rooms where id LIKE :roomId AND isDeleted = 0")
    fun getRoom(roomId: Long): RoomEntity?

    @Query("SELECT * FROM rooms WHERE isDeleted = 0 AND users LIKE '%' || :collocutorId || '%'")
    fun findRoom(collocutorId: Long): RoomEntity?
}