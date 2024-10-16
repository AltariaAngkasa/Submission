package com.dicoding.myfinalsub.data.retofit

import com.dicoding.myfinalsub.data.response.EventDetail
import com.dicoding.myfinalsub.data.response.FinishedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.*

interface ApiService {
    @GET("events")
    fun getFinished(
        @Query("active") active: Int = 1, // Menampilkan events yang aktif/akan datang
        @Query("q") query: String? = null, // Pencarian berdasarkan query (opsional)
        @Query("limit") limit: Int = 40 // Batasan jumlah event yang ditampilkan (default: 40)
    ): Call<FinishedResponse>

    @GET("events/{id}")
    fun getDetailed(
        @Path("id") id: Int
    ): Call<EventDetail>

}