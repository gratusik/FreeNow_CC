package com.gratus.ui.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.gratus.core.data.cache.AppPreferencesHelper
import com.gratus.core.util.CoreConstants.CommonConstant.CONNECTIVITY_CHANGE
import com.gratus.core.util.CoreConstants.UIConstant.IS_NETWORK_AVAILABLE
import com.gratus.core.util.CoreConstants.UIConstant.NETWORK_AVAILABLE_ACTION
import com.gratus.core.util.network.AppInterceptor
import com.gratus.core.util.networkManager.NetworkOnlineCheck
import com.gratus.core.util.networkManager.NetworkOnlineReceiver
import com.gratus.ui.R
import com.gratus.ui.view.CustomSnackBar
import javax.inject.Inject

abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes
    private val layoutId: Int
) : AppCompatActivity() {
    @Inject
    lateinit var prefs: AppPreferencesHelper

    @Inject
    lateinit var mInterceptor: AppInterceptor

    @Inject
    lateinit var networkOnlineCheck: NetworkOnlineCheck

    private var snackBar: Snackbar? = null
    private var initial: Boolean = false

    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        onInitDependencyInjection()
        onInitDataBinding()
    }

    abstract fun onInitDependencyInjection()
    abstract fun onInitDataBinding()

    // Broadcast receiver to get network state
    private fun networkReceiver() {
        val myReceiver = NetworkOnlineReceiver(networkOnlineCheck)
        val filter = IntentFilter()
        filter.addAction(CONNECTIVITY_CHANGE)
        registerReceiver(myReceiver, filter)
    }

    // network check for internet connection
    fun networkCheck(view: View, bottomNavigation: BottomNavigationView?) {
        networkReceiver()
        val intentFilter =
            IntentFilter(NETWORK_AVAILABLE_ACTION)
        LocalBroadcastManager.getInstance(this).registerReceiver(
            object : BroadcastReceiver() {
                @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
                override fun onReceive(context: Context?, intent: Intent) {
                    val isNetworkAvailable =
                        intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false)
                    if (!isNetworkAvailable) {
                        initial = true
                    }
                    if (initial) {
                        showSnack(isNetworkAvailable, view, bottomNavigation)
                        initial = isNetworkAvailable
                    }
                    initial = true
                }
            },
            intentFilter
        )
    }

    // show snack bar based on network connection boolean value
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    internal fun showSnack(
        networkConnected: Boolean,
        parent: View?,
        bottomNavigation: BottomNavigationView?
    ) {
        if (networkConnected) {
            snackBar = Snackbar.make(parent!!, R.string.network_online, Snackbar.LENGTH_SHORT)
            CustomSnackBar().getSnackBarCustom(
                bottomNavigation,
                snackBar!!,
                snackBar!!.view,
                true,
                resources.getColor(R.color.black)
            )
        } else {
            snackBar = Snackbar.make(parent!!, R.string.network_offline, Snackbar.LENGTH_INDEFINITE)
            CustomSnackBar().getSnackBarCustom(
                bottomNavigation,
                snackBar!!,
                snackBar!!.view,
                false,
                resources.getColor(R.color.black)
            )
        }
    }

    fun getPreferences(): AppPreferencesHelper {
        return prefs
    }
}
