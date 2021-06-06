package com.gratus.home.data

import com.gratus.browse.data.remote.GetDirectionSourceImpl
import com.gratus.browse.data.remote.GetVehiclePointSourceImpl
import com.gratus.core.domain.remote.DirectionResponse
import com.gratus.core.domain.remote.PointListResponse
import com.gratus.core.util.network.NetworkBoundResource
import com.gratus.core.util.network.Resource
import com.gratus.home.domain.HomeRepository
import kotlinx.coroutines.flow.Flow

class HomeRepoImpl(
    private val getVehiclePointSourceImpl: GetVehiclePointSourceImpl,
    private val getDirectionSourceImpl: GetDirectionSourceImpl
) : HomeRepository {
    override suspend fun getVehiclePoints(
        p1Lat: Double,
        p1Long: Double,
        p2Lat: Double,
        p2Long: Double
    ): Flow<Resource<PointListResponse>> {
        return object : NetworkBoundResource<PointListResponse>() {
            var localResponse: PointListResponse? = null
            override suspend fun remoteFetch(): PointListResponse {
                return getVehiclePointSourceImpl.getVehiclePoint(
                    p1Lat,
                    p1Long,
                    p2Lat,
                    p2Long
                )
            }

            override suspend fun localFetch(): PointListResponse? {
                return localResponse
            }

            override fun shouldFetchWithLocalData() = false
            override suspend fun saveFetchResult(data: PointListResponse) {
            }
        }.asFlow()
    }

    override suspend fun getDirections(
        apiKey: String,
        start: String,
        end: String
    ): Flow<Resource<DirectionResponse>> {
        return object : NetworkBoundResource<DirectionResponse>() {
            var localResponse: DirectionResponse? = null
            override suspend fun remoteFetch(): DirectionResponse {
                return getDirectionSourceImpl.getDirection(
                    apiKey,
                    start,
                    end
                )
            }

            override suspend fun localFetch(): DirectionResponse? {
                return localResponse
            }

            override fun shouldFetchWithLocalData() = false
            override suspend fun saveFetchResult(data: DirectionResponse) {
            }
        }.asFlow()
    }
}
