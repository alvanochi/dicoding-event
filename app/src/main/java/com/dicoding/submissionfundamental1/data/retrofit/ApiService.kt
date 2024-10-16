package com.dicoding.submissionfundamental1.data.retrofit
import com.dicoding.submissionfundamental1.data.response.DetailEvent
import com.dicoding.submissionfundamental1.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getUpcomingEvent(
        @Query("active") active: Int = 1,
        @Query("limit") limit: Int? = null,
        @Query("q") query: String? = null
    ): Call<EventResponse>

    @GET("events")
    fun getFinishedEvent(
        @Query("active") active: Int = 0,
        @Query("limit") limit: Int? = null,
        @Query("q") query: String? = null

    ): Call<EventResponse>


    @GET("events/{id}")
    fun getEventDetail(
        @Path("id") id: String
    ): Call<DetailEvent>

    @GET("events")
    fun getFilter(
        @Query("active") active: Int = -1,
        @Query("q") query: String?
    ): Call<EventResponse>

//    @FormUrlEncoded
//    @Headers("Authorization: token 12345")
//    @POST("review")
//    fun postReview(
//        @Field("id") id: String,
//        @Field("name") name: String,
//        @Field("review") review: String
//    ): Call<PostReviewResponse>
}