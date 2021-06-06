package com.gratus.core.domain.remote

data class Metadata(
    var attribution: String,
    var engine: Engine,
    var query: Query,
    var service: String,
    var timestamp: Long
)