package com.dicoding.submissionfundamental1.data.remote.retrofit
import com.dicoding.submissionfundamental1.data.remote.response.DetailEvent
import com.dicoding.submissionfundamental1.data.remote.response.EventResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("events")
    suspend fun getEvents(
        @Query("active") active: Int = -1,
        @Query("limit") limit: Int? = null,
        @Query("q") query: String? = null
    ): EventResponse

    @GET("events")
    suspend fun getUpcomingEvent(
        @Query("active") active: Int = 1,
        @Query("limit") limit: Int? = null,
        @Query("q") query: String? = null
    ): EventResponse

    @GET("events")
    suspend fun getFinishedEvent(
        @Query("active") active: Int = 0,
        @Query("limit") limit: Int? = null,
        @Query("q") query: String? = null

    ): EventResponse


    @GET("events/{id}")
    suspend fun getEventDetail(
        @Path("id") id: String
    ): DetailEvent

}