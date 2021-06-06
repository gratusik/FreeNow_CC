package com.gratus.browse.data.remote

import com.gratus.core.data.remote.api.VehiclePointsService
import com.gratus.core.domain.remote.PointListResponse
import javax.inject.Inject

// data source which hits the service api
class GetVehiclePointSourceImpl @Inject constructor(
    private var vehiclePointsService: VehiclePointsService
) : GetVehiclePointSource {
    override suspend fun getVehiclePoint(
        p1Lat: Double,
        p1Long: Double,
        p2Lat: Double,
        p2Long: Double
    ): PointListResponse {
        return vehiclePointsService.getVehiclePoints(
            p1Lat,
            p1Long,
            p2Lat,
            p2Long
        )
    }
}
