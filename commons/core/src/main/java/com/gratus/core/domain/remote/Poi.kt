package com.gratus.core.domain.remote

import com.google.gson.annotations.SerializedName

data class Poi(
    var dropOff: String,
    var pickup: String,
    var dropOffString: String,
    var pickupString: String,
    var pickupVisible: Boolean = false,
    var distance: String,
    var traffic: String,
    var capacity: String,
    var vacant: String,
    var meter: String,
    var coordinate: Coordinate,
    @SerializedName("fleetType")
    var fleetType: String,
    var rating: String,
    var heading: Double,
    var id: Int
)
