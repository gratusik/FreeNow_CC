package com.gratus.core.domain.remote

data class Segment(
    var distance: Double,
    var duration: Double,
    var steps: List<Step>
)