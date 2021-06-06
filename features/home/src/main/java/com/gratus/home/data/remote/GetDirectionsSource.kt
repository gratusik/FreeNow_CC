package com.gratus.browse.data.remote

import com.gratus.core.domain.remote.DirectionResponse

// data source which hits the service api
interface GetDirectionsSource {
    suspend fun getDirection(
        apiKey: String,
        start: String,
        end: String
    ): DirectionResponse
}
