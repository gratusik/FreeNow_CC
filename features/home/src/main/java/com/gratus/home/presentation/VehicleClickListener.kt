package com.gratus.home.presentation

import com.gratus.core.domain.remote.Poi

interface VehicleClickListener {
    fun onItemClick(vehicleItem: Poi, viewModel: FragmentHomeViewModel?, key: String)
}
