package com.gratus.home.util

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager
import com.gratus.core.domain.remote.Poi
import com.gratus.core.util.CoreConstants
import com.gratus.core.util.CoreConstants.UIConstant.POOLING
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
    var mClusterMarkers: ArrayList<MarkerCluster> = ArrayList()
    var mClusterManager: ClusterManager<MarkerCluster>? = null
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
            if (points.vacant.toInt() > 0) {
                try {
                    var markerIcon = R.drawable.ic_pooling_map_icon
                    if (points.fleetType == TAXI) {
                        markerIcon = R.drawable.ic_taxi_map_icon
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
        title: String,
        riding: Boolean = false,
    ): Marker? {
        mGoogleMap.isBuildingsEnabled = false
        val markerOptions = MarkerOptions()
        // Setting the position for the marker
        markerOptions.position(latLng)
        markerOptions.snippet(snippet)
        markerOptions.title(title)
        when (snippet) {
            "PickUp point" -> {
                markerOptions.icon(
                    BitmapDescriptorFactory.fromBitmap(
                        createCustomMarker(
                            snippet,
                            title
                        )
                    )
                )
            }
            "Destination" -> {
                markerOptions.icon(
                    BitmapDescriptorFactory.fromBitmap(
                        createCustomMarker(
                            snippet,
                            title
                        )
                    )
                )
            }
            TAXI -> {
                if (riding) {
                    markerOptions.icon(
                        BitmapDescriptorFactory.fromBitmap(
                            createCustomMarker(
                                snippet,
                                title
                            )
                        )
                    )
                } else {
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.mark))
                    setOnClickCameraView(latLng, mGoogleMap.addMarker(markerOptions))
                }
            }
            else -> {
                if (riding) {
                    markerOptions.icon(
                        BitmapDescriptorFactory.fromBitmap(
                            createCustomMarker(
                                snippet,
                                title
                            )
                        )
                    )
                } else {
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.mark))
                    setOnClickCameraView(latLng, mGoogleMap.addMarker(markerOptions))
                }
            }
        }
        return mGoogleMap.addMarker(markerOptions)
    }

    fun setTripCameraView(
        mTripMarkers: ArrayList<Marker>?,
        latLng: LatLng?
    ) {
        var latLong: LatLng? = latLng
        if (mTripMarkers != null) {
            latLong = LatLng(
                mTripMarkers[mTripMarkers.size - 1].position.latitude,
                mTripMarkers[mTripMarkers.size - 1].position.longitude
            )
            mTripMarkers[mTripMarkers.size - 1].showInfoWindow()
        }
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong!!, 9.1f))
    }

    fun removeTripMarkers(mTripMarkers: ArrayList<Marker>) {
        for (marker in mTripMarkers) {
            marker.remove()
        }
    }

    fun getAddress(mContext: Context, mLatLngPick: LatLng): String? {
        val geocoder = Geocoder(mContext, Locale.ENGLISH)
        val addressList: MutableList<Address> =
            geocoder.getFromLocation(mLatLngPick.latitude, mLatLngPick.longitude, 1)
        return if (addressList.size > 0) {
            addressList[0].getAddressLine(0)
        } else {
            null
        }
    }

    fun drawPolyline(
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

    fun tripPolyline(
        polyline: ArrayList<Polyline>
    ) {
        for (line in polyline) {
            mGoogleMap.addPolyline(PolylineOptions().addAll(line.points).color(line.color))
        }
    }

    fun removeTripPolyline(polyline: ArrayList<Polyline>) {
        for (line in polyline) {
            line.remove()
        }
    }

    private fun setOnClickCameraView(latLng: LatLng, marker: Marker) {
        marker.showInfoWindow()
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
    }

    private fun createCustomMarker(snippet: String?, title: String): Bitmap {
        val marker: View =
            (mContext!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.custom_marker,
                null
            )
        val snippetTv = marker.findViewById<View>(R.id.snippet_Tv) as TextView
        snippetTv.text = snippet
        val ratingTv = marker.findViewById<View>(R.id.rating_tv) as TextView
        val markerRide = marker.findViewById<View>(R.id.marker_ride) as ImageView
        val markerCircle = marker.findViewById<View>(R.id.marker_circle_image) as ImageView
        when (snippet) {
            TAXI -> {
                markerRide.isVisible = true
                markerCircle.isVisible = false
                markerRide.setImageResource(R.drawable.ic_taxi_map_icon)
                ratingTv.text = title
            }
            POOLING -> {
                markerRide.isVisible = true
                markerCircle.isVisible = false
                markerRide.setImageResource(R.drawable.ic_pooling_map_icon)
                ratingTv.text = title
            }
            else -> {
                markerRide.isVisible = false
                markerCircle.isVisible = true
            }
        }
        marker.layoutParams = ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT)
        marker.measure(marker.measuredWidth, marker.measuredHeight)
        marker.layout(0, 0, marker.measuredWidth, marker.measuredHeight)
        val bitmap = Bitmap.createBitmap(
            marker.measuredWidth,
            marker.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        marker.draw(canvas)
        return bitmap
    }
}
