package com.gratus.home.interactors

import com.gratus.core.domain.remote.PointListResponse
import com.gratus.core.util.network.Resource
import com.gratus.home.domain.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetVehiclePointUseCase(private val repo: HomeRepository) {
    suspend fun execute(
        p1Lat: Double,
        p1Long: Double,
        p2Lat: Double,
        p2Long: Double
    ): Flow<Resource<PointListResponse>> {
        return repo.getVehiclePoints(p1Lat, p1Long, p2Lat, p2Long)
    }
}
