package com.gratus.core.domain.remote

data class Step(
    var distance: Double,
    var duration: Double,
    var instruction: String,
    var name: String,
    var type: Int,
    var way_points: List<Int>
)