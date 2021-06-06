package com.gratus.core.data.remote.api

import com.gratus.core.domain.remote.PointListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface VehiclePointsService {
    @GET("/?")
    suspend fun getVehiclePoints(
        @Query("p1Lat") p1Lat: Double,
        @Query("p1Lon") p1Lon: Double,
        @Query("p2Lat") p2Lat: Double,
        @Query("p2Lon") p2Lon: Double
    ): PointListResponse
}
