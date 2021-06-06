package com.gratus.home.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.gratus.core.BaseApplication
import com.gratus.core.util.CoreConstants.RemoteConstant.BASE_URL
import com.gratus.core.util.CoreConstants.RemoteConstant.P1LAT
import com.gratus.core.util.CoreConstants.RemoteConstant.P1LONG
import com.gratus.core.util.CoreConstants.RemoteConstant.P2LAT
import com.gratus.core.util.CoreConstants.RemoteConstant.P2LONG
import com.gratus.core.util.CoreConstants.UIConstant.LOCATION_PERMISSION_REQUEST_CODE
import com.gratus.core.util.network.ResourceState
import com.gratus.home.R
import com.gratus.home.databinding.FragmentHomeBinding
import com.gratus.home.di.DaggerHomeComponent
import com.gratus.home.util.MapUtil
import com.gratus.home.util.PermissionUtils
import com.gratus.ui.base.BaseFragmentViewModel
import com.pranavpandey.android.dynamic.toasts.DynamicToast

class FragmentHome :
    BaseFragmentViewModel<FragmentHomeBinding, FragmentHomeViewModel>(
        layoutId = R.layout.fragment_home
    ),
    OnMapReadyCallback {
    private var mContext: Context? = null
    private var delay = 1000
    private lateinit var mapUtil: MapUtil
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
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    mInterceptor.setInterceptor(BASE_URL)
                    viewModel.getVehiclePoint(P1LAT, P1LONG, P2LAT, P2LONG)
                },
                delay.toLong()
            )
            observeOnVehiclePoints()
        }
        binding.bottomWhere.searchDstTv.setOnClickListener {
            view?.let { view ->
                Navigation.findNavController(view)
                    .navigate(R.id.action_navigation_home_to_navigation_places)
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

    private fun setUpLocationListener() {
        if (ActivityCompat.checkSelfPermission(
                mContext!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // request permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(mContext!!)
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location ->
                mapUtil.setCameraView(location)
            }
            .addOnFailureListener {
                DynamicToast.makeError(
                    mContext!!,
                    resources.getString(R.string.location_permission_not_granted)
                ).show()
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
        when {
            PermissionUtils.isAccessFineLocationGranted(mContext!!) -> {
                when {
                    PermissionUtils.isLocationEnabled(mContext!!) -> {
                        setUpLocationListener()
                    }
                    else -> {
                        PermissionUtils.showGPSNotEnabledDialog(mContext!!)
                    }
                }
            }
            else -> {
                PermissionUtils.requestAccessFineLocationPermission(
                    requireActivity() as AppCompatActivity,
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    when {
                        PermissionUtils.isLocationEnabled(mContext!!) -> {
                            setUpLocationListener()
                        }
                        else -> {
                            PermissionUtils.showGPSNotEnabledDialog(mContext!!)
                        }
                    }
                } else {
                    DynamicToast.makeError(
                        mContext!!,
                        resources.getString(R.string.location_permission_not_granted)
                    ).show()
                }
            }
        }
    }
}
