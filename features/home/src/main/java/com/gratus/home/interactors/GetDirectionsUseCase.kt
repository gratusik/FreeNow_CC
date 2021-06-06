package com.gratus.home.interactors

import com.gratus.core.domain.remote.DirectionResponse
import com.gratus.core.util.network.Resource
import com.gratus.home.domain.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetDirectionsUseCase(private val repo: HomeRepository) {
    suspend fun execute(
        apiKey: String,
        start: String,
        end: String
    ): Flow<Resource<DirectionResponse>> {
        return repo.getDirections(
            apiKey,
            start,
            end
        )
    }
}
