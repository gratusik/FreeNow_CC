package com.gratus.home.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.gratus.core.domain.remote.DirectionResponse
import com.gratus.core.domain.remote.PointListResponse
import com.gratus.core.util.CoreConstants
import com.gratus.core.util.CoreConstants.UIConstant.POOLING
import com.gratus.core.util.CoreConstants.UIConstant.TAXI
import com.gratus.core.util.network.Resource
import com.gratus.home.interactors.GetDirectionsUseCase
import com.gratus.home.interactors.GetVehiclePointUseCase
import com.gratus.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

class FragmentHomeViewModel @Inject constructor(
    private val getVehiclePointUseCase: GetVehiclePointUseCase,
    private val getDirectionsUseCase: GetDirectionsUseCase
) :
    BaseViewModel() {
    var vehiclePointsFlowState: MutableStateFlow<Resource<PointListResponse>> =
        MutableStateFlow(Resource.none())
    var directionsFlowState: MutableStateFlow<Resource<DirectionResponse>> =
        MutableStateFlow(Resource.none())

    // Checking latest comic ID api to comic response
    fun getVehiclePoint(
        p1Lat: Double,
        p1Long: Double,
        p2Lat: Double,
        p2Long: Double
    ) {
        vehiclePointsFlowState.value = Resource.loading()
        viewModelScope.launch {
            getVehiclePointUseCase.execute(
                p1Lat,
                p1Long,
                p2Lat,
                p2Long
            ).collect {
                addVehicleDetails(it.data, null, false, 0)
                vehiclePointsFlowState.value = it
            }
        }
    }

    fun getDirections(
        apiKey: String,
        start: String,
        end: String,
        pickup: Boolean,
        itemId: Int
    ) {
        directionsFlowState.value = Resource.loading()
        viewModelScope.launch {
            getDirectionsUseCase.execute(
                apiKey,
                start,
                end
            ).collect {
                addVehicleDetails(vehiclePointsFlowState.value.data, it.data, pickup, itemId)
                directionsFlowState.value = it
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun addVehicleDetails(
        points: PointListResponse?,
        direction: DirectionResponse?,
        pickup: Boolean,
        itemId: Int
    ) {
        if (direction == null) {
            for (item in points!!.poiList.indices) {
                points.poiList[item].rating =
                    String.format("%.1f", Random.nextDouble(0.0, 5.0))
                val position: Int = Random.nextInt(CoreConstants.UIConstant.traffic.size)
                points.poiList[item].traffic = CoreConstants.UIConstant.traffic[position]
                points.poiList[item].capacity = Random.nextInt(2, 6).toString()
                points.poiList[item].vacant =
                    (points.poiList[item].capacity.toInt() - Random.nextInt(0, 6)).toString()
                if (points.poiList[item].fleetType == TAXI) {
                    points.poiList[item].meter = "per Km 9-10€"
                } else {
                    points.poiList[item].meter = "per Km 4-5€"
                }
            }
        } else {
            for (item in points!!.poiList.indices) {
                if (pickup) {
                    if (points.poiList[item].id == itemId) {
                        val dropoff: Long =
                            direction.features[0].properties.summary.duration.toLong() + points.poiList[item].dropOff.toDouble()
                                .toLong()
                        val hours = dropoff.toInt() / 3600
                        val remainder = dropoff.toInt() - hours * 3600
                        val mins = remainder / 60
                        val calendar: Calendar = Calendar.getInstance()
                        calendar.add(Calendar.HOUR, hours).toString()
                        calendar.add(Calendar.MINUTE, mins).toString()
                        val formatter = SimpleDateFormat("hh:mm a")
                        points.poiList[item].dropOffString =
                            "DropOff: " + formatter.format(calendar.time)
                        val pickupLong: Long =
                            direction.features[0].properties.summary.duration.toLong()
                        val hoursPickUp = pickupLong.toInt() / 3600
                        val remainderPickUp = pickupLong.toInt() - hoursPickUp * 3600
                        val minsPickUp = remainderPickUp / 60
                        if (hoursPickUp > 0 && minsPickUp > 1) {
                            points.poiList[item].pickupString =
                                "Pickup: $hoursPickUp hour $minsPickUp mins"
                        } else if (hoursPickUp > 0) {
                            points.poiList[item].pickupString = "Pickup: $hoursPickUp hour"
                        } else if (minsPickUp > 1) {
                            points.poiList[item].pickupString = "Pickup: $minsPickUp mins"
                        }
                        points.poiList[item].pickupVisible = true
                        if (points.poiList[item].fleetType == TAXI) {
                            points.poiList[item].price =
                                (
                                    (
                                        points.poiList[item].distance.replace("Km", "")
                                            .toFloat()
                                        ) * 10
                                    ).toString()
                        }
                        if (points.poiList[item].fleetType == POOLING) {
                            points.poiList[item].price =
                                (
                                    (
                                        points.poiList[item].distance.replace("Km", "")
                                            .toFloat()
                                        ) * 5
                                    ).toString()
                        }
                    } else {
                        points.poiList[item].pickupVisible = false
                    }
                } else {
                    points.poiList[item].dropOff =
                        direction.features[0].properties.summary.duration.toString()
                    points.poiList[item].distance =
                        String.format(
                        "%.1f",
                        direction.features[0].properties.summary.distance / 1000
                    ) + "Km"
                }
            }
        }
    }
}
