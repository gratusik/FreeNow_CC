package com.gratus.home.domain

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.gratus.core.domain.remote.Poi

class MarkerCluster() : ClusterItem {
    private var position: LatLng? = null
    private var title: String? = null
    private var snippet: String? = null
    private var iconPicture = 0
    private var poi: Poi? = null

    constructor(
        vehiclePosition: LatLng?,
        title: String?,
        snippet: String?,
        iconPicture: Int,
        poi: Poi?
    ) : this() {
        this.position = vehiclePosition
        this.title = title
        this.snippet = snippet
        this.iconPicture = iconPicture
        this.poi = poi
    }

    fun getIconPicture(): Int {
        return iconPicture
    }

    fun setIconPicture(iconPicture: Int) {
        this.iconPicture = iconPicture
    }

    fun getPoi(): Poi? {
        return poi
    }

    fun setPoi(poi: Poi) {
        this.poi = poi
    }

    fun setPosition(position: LatLng?) {
        this.position = position
    }

    override fun getPosition(): LatLng? {
        return position
    }

    override fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    override fun getSnippet(): String? {
        return snippet
    }

    fun setSnippet(snippet: String?) {
        this.snippet = snippet
    }
}
