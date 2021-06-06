package com.gratus.home.presentation

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.gratus.core.BaseApplication
import com.gratus.core.util.CoreConstants.RemoteConstant.P1LAT
import com.gratus.core.util.CoreConstants.RemoteConstant.P1LONG
import com.gratus.core.util.CoreConstants.RemoteConstant.P2LAT
import com.gratus.core.util.CoreConstants.RemoteConstant.P2LONG
import com.gratus.home.R
import com.gratus.home.databinding.FragmentPlacesBinding
import com.gratus.home.di.DaggerHomeComponent
import com.gratus.home.util.MapUtil
import com.gratus.ui.base.BaseFragmentViewModel

class FragmentPlaces :
    BaseFragmentViewModel<FragmentPlacesBinding, FragmentHomeViewModel>(
        layoutId = R.layout.fragment_places
    ),
    OnMapReadyCallback {
    private var mGoogleMap: GoogleMap? = null
    private var mContext: Context? = null
    private lateinit var mapUtil: MapUtil
    private val mTripMarkers: ArrayList<Marker> = ArrayList()
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
        binding.doneBt.setOnClickListener {
            view?.let { view ->
                Navigation.findNavController(view)
                    .navigate(R.id.action_navigation_places_to_navigation_ride)
            }
        }
        binding.placesLayout.backImg.setOnClickListener {
            view?.let { it1 -> Navigation.findNavController(it1).popBackStack() }
        }
    }

    private fun initGoogleMap(savedInstanceState: Bundle?) {
        binding.mapViewInc.mapView.onCreate(savedInstanceState)
        binding.mapViewInc.mapView.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        mGoogleMap = map
        mapUtil = MapUtil(mContext, requireActivity(), map)
        mapUtil.mapReady()
        mapUtil.removeTripMarkers(mTripMarkers)
        setPickUpDst()
    }

    private fun setPickUpDst() {
        var mLatLng = LatLng(P1LAT, P1LONG)
        binding.placesLayout.fromEt.setText(mapUtil.getAddress(mContext!!, mLatLng))
        mTripMarkers.add(mapUtil.addTripMarker(mLatLng, "PickUp point")!!)
        mLatLng = LatLng(P2LAT, P2LONG)
        binding.placesLayout.toEt.setText(mapUtil.getAddress(mContext!!, mLatLng))
        val marker = mapUtil.addTripMarker(mLatLng, "Destination")
        marker!!.showInfoWindow()
        mTripMarkers.add(marker)
        mapUtil.setTripCameraView(mTripMarkers, null)
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
}
