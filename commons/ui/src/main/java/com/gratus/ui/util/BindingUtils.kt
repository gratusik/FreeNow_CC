package com.gratus.ui.util

import android.text.TextWatcher
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.gratus.core.domain.remote.Poi
import com.gratus.core.util.CoreConstants.UIConstant.TAXI
import com.gratus.ui.R

object BindingUtils {
    @JvmStatic
    fun setEnableButton(materialButton: MaterialButton, enable: Boolean) {
        materialButton.isEnabled = enable
    }

    @JvmStatic
    @BindingAdapter("errorText")
    fun setErrorMessage(
        textInputLayout: TextInputLayout,
        strOrResId: Any?
    ) {
        if (strOrResId is Int) {
            textInputLayout.error = textInputLayout.context.getString((strOrResId as Int?)!!)
        } else {
            textInputLayout.error = strOrResId as String?
        }
    }

    @JvmStatic
    @BindingAdapter("textChangedListener")
    fun bindTextWatcher(editText: TextInputEditText, textWatcher: TextWatcher?) {
        editText.addTextChangedListener(textWatcher)
    }

    @JvmStatic
    @BindingAdapter("setVisibility")
    fun setLineLayoutVacantVisibility(linearLayout: LinearLayout, points: Poi) {
        if (points.fleetType == TAXI) {
            linearLayout.isVisible = false
        } else {
            linearLayout.isVisible = points.vacant.toInt() > 0
        }
    }

    @JvmStatic
    @BindingAdapter("app:setImage")
    fun setImageResource(imageView: ImageView, fleetType: String) {
        if (fleetType == TAXI) {
            imageView.setImageResource(R.drawable.ic_taxi_icon)
        } else {
            imageView.setImageResource(R.drawable.ic_pooling_icon)
        }
    }

    @JvmStatic
    @BindingAdapter("setLinearBackground")
    fun setLinearBackground(linearLayout: LinearLayout, rating: String) {
        when {
            rating.toDouble() >= 4.0 -> {
                linearLayout.setBackgroundResource(R.drawable.ratings_green_background)
            }
            rating.toDouble() >= 2.0 -> {
                linearLayout.setBackgroundResource(R.drawable.ratings_yellow_background)
            }
            else -> {
                linearLayout.setBackgroundResource(R.drawable.ratings_red_background)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setLinearBackgroundTraffic")
    fun setLinearBackgroundTraffic(linearLayout: LinearLayout, traffic: String) {
        when (traffic) {
            "High" -> {
                linearLayout.setBackgroundResource(R.drawable.ratings_red_background)
            }
            "Moderate" -> {
                linearLayout.setBackgroundResource(R.drawable.ratings_yellow_background)
            }
            else -> {
                linearLayout.setBackgroundResource(R.drawable.ratings_green_background)
            }
        }
    }
}
