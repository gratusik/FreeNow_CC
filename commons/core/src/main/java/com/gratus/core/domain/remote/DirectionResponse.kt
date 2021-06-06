package com.gratus.core.domain.remote

data class DirectionResponse(
    var bbox: List<Double>,
    var features: List<Feature>,
    var metadata: Metadata,
    var type: String
)
