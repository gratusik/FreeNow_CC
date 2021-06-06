package com.gratus.core.data.remote.api

import com.gratus.core.domain.remote.DirectionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectionsService {
    @GET("/v2/directions/driving-car?")
    suspend fun getDirection(
        @Query("api_key") apiKey: String,
        @Query("start") start: String,
        @Query("end") end: String
    ): DirectionResponse
}
