package com.gratus.browse.data.remote

import com.gratus.core.data.remote.api.DirectionsService
import com.gratus.core.domain.remote.DirectionResponse
import javax.inject.Inject

// data source which hits the service api
class GetDirectionSourceImpl @Inject constructor(
    private var directionsService: DirectionsService
) : GetDirectionsSource {
    override suspend fun getDirection(
        apiKey: String,
        start: String,
        end: String
    ): DirectionResponse {
        return directionsService.getDirection(
            apiKey,
            start,
            end
        )
    }
}
