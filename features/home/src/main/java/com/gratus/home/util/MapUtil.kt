package com.gratus.home.util

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager
import com.gratus.core.domain.remote.Poi
import com.gratus.core.util.CoreConstants
import com.gratus.core.util.CoreConstants.UIConstant.TAXI
import com.gratus.home.R
import com.gratus.home.domain.MarkerCluster
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MapUtil @Inject constructor(
    var mContext: Context?,
    var activity: FragmentActivity,
    var mGoogleMap: GoogleMap
) {
    private var mClusterMarkers: ArrayList<MarkerCluster> = ArrayList()
    private var mClusterManager: ClusterManager<MarkerCluster>? = null
    private var mapClusterRenderer: MapClusterRenderer? = null
    fun addPointsMapMarkers(
        poiList: List<Poi>
    ) {
        if (mClusterManager == null) {
            mClusterManager = ClusterManager<MarkerCluster>(mContext, mGoogleMap)
        }
        if (mapClusterRenderer == null) {
            mapClusterRenderer =
                MapClusterRenderer(
                    mContext!!,
                    mGoogleMap,
                    mClusterManager!!
                )
            mClusterManager!!.renderer = mapClusterRenderer
        }

        for (points in poiList) {
            try {
                var markerIcon = R.drawable.ic_pooling_icon
                if (points.fleetType == TAXI) {
                    markerIcon = R.drawable.ic_taxi_icon
                }
                var newClusterMarker =
                    MarkerCluster()
                if ((points.capacity).toInt() > 0) {
                    newClusterMarker =
                        MarkerCluster(
                            LatLng(points.coordinate.latitude, points.coordinate.longitude),
                            points.rating, points.fleetType, markerIcon, points
                        )
                }
                if (!mClusterMarkers.contains(points)) {
                    mClusterManager!!.addItem(newClusterMarker)
                    mClusterMarkers.add(newClusterMarker)
                }
            } catch (e: NullPointerException) {
                Log.e(ContentValues.TAG, "marker" + e.message)
            }
            mClusterManager!!.cluster()
            mGoogleMap.setInfoWindowAdapter(CustomInfoWindow(mContext!!))
        }
        val mLocation = LatLng(
            poiList[poiList.size - 1].coordinate.latitude,
            poiList[poiList.size - 1].coordinate.longitude
        )
        setTripCameraView(null, mLocation)
    }

    fun mapReady() {
        if (ActivityCompat.checkSelfPermission(
                mContext!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // request permission
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                CoreConstants.UIConstant.LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        mGoogleMap.isMyLocationEnabled = true
    }

    fun setCameraView(location: Location) {
        // Set a boundary to start
        val bottomBoundary: Double = location.latitude - .1
        val leftBoundary: Double = location.longitude - .1
        val topBoundary: Double = location.latitude + .1
        val rightBoundary: Double = location.longitude + .1
        val latLogBoundary =
            LatLngBounds(LatLng(bottomBoundary, leftBoundary), LatLng(topBoundary, rightBoundary))
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLogBoundary, 0))
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
    }

    fun addTripMarker(
        latLng: LatLng,
        snippet: String,
    ): Marker? {
        mGoogleMap.isBuildingsEnabled = false
        val markerOptions = MarkerOptions()
        // Setting the position for the marker
        markerOptions.position(latLng)
        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.snippet(snippet)
        if (snippet == "PickUp point") {
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_circle_marker_icon))
        } else {
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_dst_marker_icon))
        }
        // Animating to the touched position
        // Placing a marker on the touched position
        mGoogleMap.setInfoWindowAdapter(CustomInfoWindow(mContext!!))
        return mGoogleMap.addMarker(markerOptions)
    }

    fun setTripCameraView(
        mTripMarkers: ArrayList<Marker>?,
        latLng: LatLng?
    ) {
        var latLong: LatLng? = latLng
        if (mTripMarkers != null) {
            latLong = LatLng(
                mTripMarkers[1].position.latitude,
                mTripMarkers[1].position.longitude
            )
            mTripMarkers[1].showInfoWindow()
        }
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong!!, 9.0f))
    }

    fun removeTripMarkers(mTripMarkers: ArrayList<Marker>) {
        for (marker in mTripMarkers) {
            marker.remove()
        }
    }

    fun getAddress(mContext: Context, mLatLngPick: LatLng): String {
        val geocoder = Geocoder(mContext, Locale.ENGLISH)
        val addressList: MutableList<Address> =
            geocoder.getFromLocation(mLatLngPick.latitude, mLatLngPick.longitude, 1)
        return addressList[0].getAddressLine(0)
    }

    fun tripPolyline(
        coordinates: List<List<Double>>
    ): Polyline {
        val newDecodedPath: ArrayList<LatLng> = ArrayList()
        for (latLng in coordinates) {
            newDecodedPath.add(
                LatLng(
                    latLng[1],
                    latLng[0]
                )
            )
        }
        val polyline =
            mGoogleMap.addPolyline(PolylineOptions().addAll(newDecodedPath))
        polyline.color = ContextCompat.getColor(mContext!!, R.color.third_text)
        return polyline
    }

    fun removeTripPolyline(polyline: ArrayList<Polyline>) {
        for (line in polyline) {
            line.remove()
        }
    }
}
