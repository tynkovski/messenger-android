package com.tynkovski.apps.messenger.core.network.retrofit

import com.tynkovski.apps.messenger.core.network.model.request.CreateRoomRequest
import com.tynkovski.apps.messenger.core.network.model.response.RoomResponse
import com.tynkovski.apps.messenger.core.network.model.response.RoomsPagingResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

private const val ROOMS_API = "/room"

interface RoomsNetworkApi {
    @POST(ROOMS_API)
    suspend fun createRoom(
        @Body request: CreateRoomRequest
    ): RoomResponse

    @GET("$ROOMS_API/{id}")
    suspend fun getRoom(
        @Path(value = "id", encoded = true) roomId: Long
    ): RoomResponse

    @GET("$ROOMS_API/find/{id}")
    suspend fun findRoom(
        @Path(value = "id", encoded = true) collocutorId: Long
    ): RoomResponse

    @GET("$ROOMS_API/paged")
    suspend fun getRoomsPaged(
        @Query("page") page: Long,
        @Query("pageSize") pageSize: Int
    ): RoomsPagingResponse
}