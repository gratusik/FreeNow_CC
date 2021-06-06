package com.gratus.core.domain.remote

data class Properties(
    var segments: List<Segment>,
    var summary: Summary,
    var way_points: List<Int>
)