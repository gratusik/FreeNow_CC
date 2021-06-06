package com.gratus.core.domain.remote

data class Feature(
    var bbox: List<Double>,
    var geometry: Geometry,
    var properties: Properties,
    var type: String
)
