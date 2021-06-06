package com.gratus.home.util

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import com.gratus.home.R
import com.gratus.home.domain.MarkerCluster

class MapClusterRenderer(
    context: Context,
    googleMap: GoogleMap?,
    clusterManager: ClusterManager<MarkerCluster>
) : DefaultClusterRenderer<MarkerCluster>(context, googleMap, clusterManager) {

    private val iconGenerator: IconGenerator = IconGenerator(context.applicationContext)
    private val imageView: ImageView = ImageView(context.applicationContext)
    private val markerWidth: Int =
        context.resources.getDimension(R.dimen.custom_marker_image).toInt()
    private val markerHeight: Int =
        context.resources.getDimension(R.dimen.custom_marker_image).toInt()
    private val padding =
        context.resources.getDimension(R.dimen.custom_marker_padding).toInt()

    /**
     * Rendering of the individual ClusterItems
     * @param item
     * @param markerOptions
     */
    override fun onBeforeClusterItemRendered(item: MarkerCluster, markerOptions: MarkerOptions) {
        imageView.setImageResource(item.getIconPicture())
        val icon = iconGenerator.makeIcon()
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.getTitle())
        if (item.getPoi() != null) {
            markerOptions.rotation(item.getPoi()!!.heading.toFloat())
        }
    }

    override fun shouldRenderAsCluster(cluster: Cluster<MarkerCluster>): Boolean {
        return false
    }

    init {
        imageView.layoutParams = ViewGroup.LayoutParams(markerWidth, markerHeight)
        imageView.setPadding(padding, padding, padding, padding)
        iconGenerator.setContentView(imageView)
        iconGenerator.setBackground(null)
    }
}
