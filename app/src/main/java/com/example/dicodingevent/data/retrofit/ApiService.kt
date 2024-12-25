package com.example.dicodingevent.data.retrofit

import com.example.dicodingevent.data.response.UpcomingResponse
import com.example.dicodingevent.data.response.DetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class ApiService {
    interface ApiService {
        @GET("com/example/dicodingevent/ui/detail/{id}")
        fun getListEventsItem(
            @Path("id") id: String
        ): Call<UpcomingResponse>

        @GET("events")
        fun getFinishEvents(
            @Query("active")
            active: Int = 0
        ): Call<UpcomingResponse>

        @GET("events/{id}")
        fun getDetailEvent(
            @Path("id") id: Int
        ): Call<UpcomingResponse>
    }
}
