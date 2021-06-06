package com.gratus.home.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.gratus.home.R

class CustomInfoWindow(context: Context) : GoogleMap.InfoWindowAdapter {
    private var mContext: Context = context
    override fun getInfoWindow(marker: Marker): View {
        val inflater: LayoutInflater =
            mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View = inflater.inflate(R.layout.map_info_window, null)
        val snippetTv = v.findViewById<TextView>(R.id.snippet_Tv)
        val ratingTv = v.findViewById<TextView>(R.id.rating_tv)
        ratingTv.text = marker.title
        snippetTv.text = marker.snippet
        return v
    }

    override fun getInfoContents(p0: Marker): View? {
        return null
    }
}
