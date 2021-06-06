package com.gratus.ui.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.stfalcon.frescoimageviewer.ImageViewer

class ImageLoaderUtil {
    // using glide to load image from url and store as cache
    fun loadGlideIntoImageView(link: String?, view: ImageView) {
        Glide.with(view)
            .asDrawable()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .load(link)
            .into(view)
    }

    // comic Image on click moved to full screen for readability
    fun showFrescoImage(
        view: View,
        link: String?,
        marginDimension: Int
    ) {

        ImageViewer.Builder<String>(view.context, listOf(link))
            .hideStatusBar(false)
            .setImageMargin(view.context, marginDimension)
            .show()
    }
}
