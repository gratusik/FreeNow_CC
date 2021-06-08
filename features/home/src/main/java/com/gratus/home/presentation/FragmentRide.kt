package com.gratus.home.presentation

import android.content.Context
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import com.gratus.core.BaseApplication
import com.gratus.core.domain.remote.Poi
import com.gratus.core.util.CoreConstants.RemoteConstant.BASE_URL
import com.gratus.core.util.CoreConstants.RemoteConstant.DIRECTION_BASE_URL
import com.gratus.core.util.CoreConstants.RemoteConstant.P1LAT
import com.gratus.core.util.CoreConstants.RemoteConstant.P1LONG
import com.gratus.core.util.CoreConstants.RemoteConstant.P2LAT
import com.gratus.core.util.CoreConstants.RemoteConstant.P2LONG
import com.gratus.core.util.CoreConstants.UIConstant.POLYLINE
import com.gratus.core.util.CoreConstants.UIConstant.SELECTED
import com.gratus.core.util.network.ResourceState
import com.gratus.home.R
import com.gratus.home.databinding.FragmentRideBinding
import com.gratus.home.di.DaggerHomeComponent
import com.gratus.home.util.MapUtil
import com.gratus.ui.base.BaseFragmentViewModel
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import javax.inject.Inject

class FragmentRide :
    BaseFragmentViewModel<FragmentRideBinding, FragmentHomeViewModel>(
        layoutId = R.layout.fragment_ride
    ),
    VehicleClickListener,
    OnMapReadyCallback {
    private var mContext: Context? = null
    private val mTripMarkers: ArrayList<Marker> = ArrayList()
    private var polyline: ArrayList<Polyline> = ArrayList()
    private lateinit var mapUtil: MapUtil
    private var scroll: Boolean = false
    private var selectedVehicle: Poi? = null

    @Inject
    lateinit var vehicleListAdapter: VehicleListAdapter
    override fun onInitViewModel() {
        viewModel =
            ViewModelProvider(this, factory)
                .get(FragmentHomeViewModel::class.java)
    }

    override fun onInitDependencyInjection() {
        DaggerHomeComponent
            .builder()
            .coreComponent(BaseApplication.coreComponent)
            .build()
            .inject(this)
    }

    override fun onInitDataBinding(savedInstanceState: Bundle?) {
        initGoogleMap(savedInstanceState)
        if (isNetworkConnected()) {
            mInterceptor.setInterceptor(BASE_URL)
            viewModel.getVehiclePoint(P1LAT, P1LONG, P2LAT, P2LONG)
            observeOnVehiclePoints()
        }
        binding.backImg.setOnClickListener {
            view?.let { view ->
                Navigation.findNavController(view)
                    .navigate(R.id.action_navigation_ride_to_navigation_home)
            }
        }
        setVehicleListAdapter()
        binding.confirmRideBt.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(SELECTED, selectedVehicle)
            bundle.putSerializable(POLYLINE, polyline)
            view?.let { view ->
                Navigation.findNavController(view)
                    .navigate(R.id.action_navigation_ride_to_navigation_riding, bundle)
            }
        }
    }

    private fun initGoogleMap(savedInstanceState: Bundle?) {
        binding.mapViewInc.mapView.onCreate(savedInstanceState)
        binding.mapViewInc.mapView.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        mapUtil = MapUtil(mContext, requireActivity(), map)
        mapUtil.mapReady()
        mapUtil.removeTripMarkers(mTripMarkers)
        setPickUpDst()
        map.setOnMarkerClickListener(
            OnMarkerClickListener { marker ->
                for (i in viewModel.vehiclePointsFlowState.value.data!!.poiList) {
                    if (i.coordinate.latitude == marker.position.latitude &&
                        i.coordinate.longitude == marker.position.longitude
                    ) {
                        viewModel.getDirections(
                            resources.getString(R.string.direction_key),
                            "$P1LONG,$P1LAT",
                            "${marker.position.longitude},${marker.position.latitude}",
                            true,
                            i.id
                        )
                        scroll = true
                    }
                }
                true
            }
        )
    }

    private fun setPickUpDst() {
        var mLatLng = LatLng(P1LAT, P1LONG)
        mTripMarkers.add(mapUtil.addTripMarker(mLatLng, "PickUp point", "")!!)
        mLatLng = LatLng(P2LAT, P2LONG)
        mTripMarkers.add(mapUtil.addTripMarker(mLatLng, "Destination", "")!!)
        mapUtil.setTripCameraView(mTripMarkers, null)
    }

    // setting up the product list adapter
    private fun setVehicleListAdapter() {
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.pointsRv.layoutManager = linearLayoutManager
        binding.pointsRv.itemAnimator = DefaultItemAnimator()
        binding.pointsRv.adapter = vehicleListAdapter
        vehicleListAdapter.setListenerViewModel(FragmentRide(), viewModel)
    }

    private fun observeOnVehiclePoints() {
        viewModel.vehiclePointsFlowState.asLiveData().observe(this) {
            when (it.status) {
                ResourceState.LOADING -> {
                }
                ResourceState.SUCCESS -> {
                    mapUtil.addPointsMapMarkers(
                        it.data!!.poiList
                    )
                    mInterceptor.setInterceptor(DIRECTION_BASE_URL)
                    viewModel.getDirections(
                        resources.getString(R.string.direction_key),
                        "$P1LONG,$P1LAT",
                        "$P2LONG,$P2LAT",
                        false,
                        0
                    )
                    observeOnDirection()
                }
                ResourceState.ERROR -> {
                    DynamicToast.makeError(
                        requireContext(),
                        it.message
                    ).show()
                }
                else -> {
                }
            }
        }
    }

    private fun observeOnDirection() {
        var pos = 0
        viewModel.directionsFlowState.asLiveData().observe(this) {
            when (it.status) {
                ResourceState.LOADING -> {
                }
                ResourceState.SUCCESS -> {

                    if (polyline.size - 1 == 1) {
                        polyline[polyline.size - 1].remove()
                        polyline.removeAt(polyline.size - 1)
                    }
                    if (mTripMarkers.size - 1 == 2) {
                        mTripMarkers[mTripMarkers.size - 1].remove()
                        mTripMarkers.removeAt(mTripMarkers.size - 1)
                    }
                    for ((index, value) in viewModel.vehiclePointsFlowState.value.data!!.poiList.withIndex()) {
                        if (value.pickupVisible) {
                            pos = index
                            mTripMarkers.add(
                                mapUtil.addTripMarker(
                                    LatLng(
                                        value.coordinate.latitude,
                                        value.coordinate.longitude
                                    ),
                                    value.fleetType,
                                    value.rating
                                )!!
                            )
                            selectedVehicle = value
                            binding.confirmRideBt.isVisible = true
                        }
                    }
                    if (polyline.size <= 1) {
                        polyline.add(
                            mapUtil.drawPolyline(
                                it.data?.features?.get(0)?.geometry?.coordinates!!
                            )
                        )
                    }
                    vehicleListAdapter.updateVehicleListAdapter(viewModel.vehiclePointsFlowState.value.data!!.poiList)
                    binding.pointsRv.post {
                        if (scroll) {
                            binding.pointsRv.smoothScrollToPosition(pos)
                            scroll = false
                        }
                    }
                }
                ResourceState.ERROR -> {
                    DynamicToast.makeError(
                        requireContext(),
                        it.message
                    ).show()
                }
                else -> {
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mapViewInc.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapViewInc.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapViewInc.mapView.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        binding.mapViewInc.mapView.onStart()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapViewInc.mapView.onLowMemory()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    override fun onItemClick(
        vehicleItem: Poi,
        viewModel: FragmentHomeViewModel?,
        key: String
    ) {
        viewModel!!.getDirections(
            key,
            "$P1LONG,$P1LAT",
            "${vehicleItem.coordinate.longitude},${vehicleItem.coordinate.latitude}",
            true,
            vehicleItem.id
        )
    }
}
