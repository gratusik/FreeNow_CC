package com.gratus.home.domain

import com.gratus.core.domain.remote.DirectionResponse
import com.gratus.core.domain.remote.PointListResponse
import com.gratus.core.util.network.Resource
import kotlinx.coroutines.flow.Flow

// repo to getLatest Comic ID
interface HomeRepository {
    suspend fun getVehiclePoints(
        p1Lat: Double,
        p1Long: Double,
        p2Lat: Double,
        p2Long: Double
    ): Flow<Resource<PointListResponse>>

    suspend fun getDirections(
        apiKey: String,
        start: String,
        end: String
    ): Flow<Resource<DirectionResponse>>
}
