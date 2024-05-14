package com.tynkovski.apps.messenger.core.database.dao

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
    suspend fun upsert(entity: List<RoomEntity>)

    @Delete
    suspend fun delete(entity: RoomEntity)

    @Query("SELECT * FROM rooms WHERE isDeleted = 0 ORDER by actionDateTime DESC")
    fun getRooms(): Flow<List<RoomEntity>>

    @Query("SELECT * FROM rooms where id LIKE :roomId")
    fun getRoom(roomId: Long): Flow<RoomEntity>
}