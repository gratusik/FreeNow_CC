package com.gratus.core.domain.remote

import com.google.gson.annotations.SerializedName

data class PointListResponse(
    @SerializedName("poiList")
    var poiList: ArrayList<Poi>
)
