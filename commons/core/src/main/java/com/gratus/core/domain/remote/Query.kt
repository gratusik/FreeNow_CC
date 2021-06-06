package com.gratus.core.domain.remote

data class Query(
    var coordinates: List<List<Double>>,
    var format: String,
    var profile: String
)