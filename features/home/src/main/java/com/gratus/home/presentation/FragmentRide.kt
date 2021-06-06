package com.gratus.home.presentation

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.GoogleMap
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
            view?.let { it1 -> Navigation.findNavController(it1).popBackStack() }
        }
        setVehicleListAdapter()
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
        mapUtil.removeTripPolyline(polyline)
    }

    private fun setPickUpDst() {
        var mLatLng = LatLng(P1LAT, P1LONG)
        mTripMarkers.add(mapUtil.addTripMarker(mLatLng, "PickUp point")!!)
        mLatLng = LatLng(P2LAT, P2LONG)
        val marker = mapUtil.addTripMarker(mLatLng, "Destination")
        marker!!.showInfoWindow()
        mTripMarkers.add(marker)
        mapUtil.setTripCameraView(mTripMarkers, null)
    }

    // setting up the product list adapter
    private fun setVehicleListAdapter() {
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.pointsRv.layoutManager = linearLayoutManager
        binding.pointsRv.itemAnimator = DefaultItemAnimator()
        binding.pointsRv.adapter = vehicleListAdapter
        vehicleListAdapter.setListener(FragmentRide())
        vehicleListAdapter.setViewModel(viewModel)
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
        viewModel.directionsFlowState.asLiveData().observe(this) {
            when (it.status) {
                ResourceState.LOADING -> {
                }
                ResourceState.SUCCESS -> {
                    if (polyline.size > 2) {
                        polyline[2].remove()
                        polyline.removeAt(2)
                    }
                    polyline.add(
                        mapUtil.tripPolyline(
                            it.data?.features?.get(0)?.geometry?.coordinates!!
                        )
                    )
                    vehicleListAdapter.updateVehicleListAdapter(viewModel.vehiclePointsFlowState.value.data!!.poiList)
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

    override fun onItemClick(vehicleItem: Poi, viewModel: FragmentHomeViewModel?, key: String) {
        viewModel!!.getDirections(
            key,
            "$P1LONG,$P1LAT",
            "${vehicleItem.coordinate.longitude},${vehicleItem.coordinate.latitude}",
            true,
            vehicleItem.id
        )
//        val marker = mapUtil.addTripMarker(mLatLng, "Destination")
//        marker!!.showInfoWindow()
//        mapUtil.removeTripMarkers(mTripMarkers)
//        mTripMarkers.add(marker)
    }
}
