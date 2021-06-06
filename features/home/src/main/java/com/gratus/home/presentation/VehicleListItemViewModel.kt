package com.gratus.home.presentation

import com.gratus.core.domain.remote.Poi
import javax.inject.Inject

// Item view model to data binding of data to respective xml
class VehicleListItemViewModel @Inject constructor(
    private var vehicleItem: Poi,
    private var listener: VehicleClickListener,
    private var viewModel: FragmentHomeViewModel?,
    private var key: String,
) {
    fun getVehicleItem(): Poi {
        return vehicleItem
    }

    fun onItemClick() {
        listener.onItemClick(vehicleItem, viewModel!!, key)
    }
}
