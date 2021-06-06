package com.gratus.ui.view

import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.gratus.ui.R

// show snack bar based on network connection boolean value
class CustomSnackBar {

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun getSnackBarCustom(
        bottomNavigation: BottomNavigationView?,
        snackBar: Snackbar,
        view: View,
        b: Boolean,
        color: Int
    ) {
        val textView =
            view.findViewById<View>(R.id.snackbar_text) as TextView
        if (b) {
            textView.setTextColor(Color.WHITE)
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            snackBar.setBackgroundTint(color)
        } else {
            textView.setTextColor(Color.YELLOW)
        }
        if (bottomNavigation != null) {
            snackBar.anchorView = bottomNavigation
        }
        snackBar.show()
    }
}
