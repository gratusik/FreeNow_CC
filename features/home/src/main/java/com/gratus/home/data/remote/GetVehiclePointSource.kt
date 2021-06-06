package com.gratus.browse.data.remote

import com.gratus.core.domain.remote.PointListResponse

// data source which hits the service api
interface GetVehiclePointSource {
    suspend fun getVehiclePoint(
        p1Lat: Double,
        p1Long: Double,
        p2Lat: Double,
        p2Long: Double
    ): PointListResponse
}
