package com.gratus.home.presentation

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import com.gratus.core.BaseApplication
import com.gratus.core.domain.remote.Poi
import com.gratus.core.util.CoreConstants
import com.gratus.core.util.CoreConstants.RemoteConstant.P1LAT
import com.gratus.core.util.CoreConstants.RemoteConstant.P1LONG
import com.gratus.core.util.CoreConstants.RemoteConstant.P2LAT
import com.gratus.core.util.CoreConstants.RemoteConstant.P2LONG
import com.gratus.core.util.CoreConstants.UIConstant.POLYLINE
import com.gratus.home.R
import com.gratus.home.databinding.FragmentRidingBinding
import com.gratus.home.di.DaggerHomeComponent
import com.gratus.home.util.MapUtil
import com.gratus.ui.base.BaseFragmentViewModel

class FragmentRiding :
    BaseFragmentViewModel<FragmentRidingBinding, FragmentHomeViewModel>(
        layoutId = R.layout.fragment_riding
    ),
    OnMapReadyCallback {
    private var mContext: Context? = null
    private val mTripMarkers: ArrayList<Marker> = ArrayList()
    private var polyline: ArrayList<Polyline> = ArrayList()
    private lateinit var mapUtil: MapUtil
    var selectedVehicle: Poi? = null
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
        selectedVehicle = arguments?.getSerializable(CoreConstants.UIConstant.SELECTED) as Poi
        polyline = arguments?.getSerializable(POLYLINE) as ArrayList<Polyline>
        binding.riding = selectedVehicle
        initGoogleMap(savedInstanceState)
        binding.backImg.setOnClickListener {
            view?.let { it1 ->
                Navigation.findNavController(it1)
                    .navigate(R.id.action_navigation_riding_to_navigation_ride)
            }
        }
        bookingView()
    }

    private fun bookingView() {
        binding.confirmBooking.nowBt.setOnClickListener {
            println(polyline.size)
            mapUtil.removeTripPolyline(polyline)
            binding.confirmBooking.nowBt.setBackgroundColor(
                ContextCompat.getColor(
                    mContext!!,
                    R.color.third_text
                )
            )
            binding.confirmBooking.cardBt.setBackgroundColor(
                ContextCompat.getColor(
                    mContext!!,
                    R.color.button_background
                )
            )
            binding.confirmBooking.moneyBt.setBackgroundColor(
                ContextCompat.getColor(
                    mContext!!,
                    R.color.button_background
                )
            )
            binding.confirmBooking.priceText.text =
                ((selectedVehicle?.price!!).toFloat() - 10).toString()
            binding.confirmBooking.confirmRideBt.isVisible = true
        }
        binding.confirmBooking.cardBt.setOnClickListener {
            binding.confirmBooking.nowBt.setBackgroundColor(
                ContextCompat.getColor(
                    mContext!!,
                    R.color.button_background
                )
            )
            binding.confirmBooking.cardBt.setBackgroundColor(
                ContextCompat.getColor(
                    mContext!!,
                    R.color.third_text
                )
            )
            binding.confirmBooking.moneyBt.setBackgroundColor(
                ContextCompat.getColor(
                    mContext!!,
                    R.color.button_background
                )
            )
            binding.confirmBooking.priceText.text = selectedVehicle?.price
            binding.confirmBooking.confirmRideBt.isVisible = true
        }
        binding.confirmBooking.moneyBt.setOnClickListener {
            binding.confirmBooking.nowBt.setBackgroundColor(
                ContextCompat.getColor(
                    mContext!!,
                    R.color.button_background
                )
            )
            binding.confirmBooking.cardBt.setBackgroundColor(
                ContextCompat.getColor(
                    mContext!!,
                    R.color.button_background
                )
            )
            binding.confirmBooking.moneyBt.setBackgroundColor(
                ContextCompat.getColor(
                    mContext!!,
                    R.color.third_text
                )
            )
            binding.confirmBooking.priceText.text = selectedVehicle?.price
            binding.confirmBooking.confirmRideBt.isVisible = true
        }
        binding.confirmBooking.confirmRideBt.setOnClickListener {
            binding.confirmBooking.bookingCard.isVisible = false
            binding.otp.otpLayout.isVisible = true
            binding.ride.finalLayout.isVisible = true
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    binding.otp.pickUpText.isVisible = false
                    binding.ride.cancelBt.isVisible = false
                    binding.ride.rideText.isVisible = true
                    mTripMarkers[2].remove()
                    mTripMarkers.removeAt(2)
                    mTripMarkers[1].remove()
                    mTripMarkers.removeAt(1)
                    polyline[1].remove()
                    polyline.removeAt(1)
                    binding.ride.rideText.text = "Rider has reached the pick up point"
                    mTripMarkers.add(
                        mapUtil.addTripMarker(
                            LatLng(P1LAT, P1LONG),
                            selectedVehicle!!.fleetType,
                            selectedVehicle!!.rating,
                            true
                        )!!
                    )
                    Handler(Looper.getMainLooper()).postDelayed(
                        {
                            mTripMarkers[1].remove()
                            mTripMarkers.removeAt(1)
                            mTripMarkers[0].remove()
                            mTripMarkers.removeAt(0)
                            polyline[0].remove()
                            polyline.removeAt(0)
                            binding.ride.rideText.text = "You have reached your destination on time"
                            mTripMarkers.add(
                                mapUtil.addTripMarker(
                                    LatLng(P2LAT, P2LONG),
                                    selectedVehicle!!.fleetType,
                                    selectedVehicle!!.rating,
                                    true
                                )!!
                            )

                            view?.let { it1 ->
                                Navigation.findNavController(it1)
                                    .navigate(R.id.action_navigation_riding_to_navigation_home)
                            }
                        },
                        5000
                    )
                },
                5000
            )
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
        setPickUpDstRide()
    }

    private fun setPickUpDstRide() {
        var mLatLng = LatLng(P2LAT, P2LONG)
        mTripMarkers.add(mapUtil.addTripMarker(mLatLng, "Destination", "")!!)
        mLatLng = LatLng(P1LAT, P1LONG)
        mTripMarkers.add(mapUtil.addTripMarker(mLatLng, "PickUp point", "")!!)
        mapUtil.setTripCameraView(mTripMarkers, null)
        mLatLng =
            LatLng(selectedVehicle!!.coordinate.latitude, selectedVehicle!!.coordinate.longitude)
        mTripMarkers.add(
            mapUtil.addTripMarker(
                mLatLng,
                selectedVehicle!!.fleetType,
                selectedVehicle!!.rating,
                true
            )!!
        )
        mapUtil.tripPolyline(polyline)
        mapUtil.removeTripPolyline(polyline)
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
