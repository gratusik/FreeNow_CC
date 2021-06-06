package com.gratus.core.util.networkManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.gratus.core.util.CoreConstants.UIConstant.IS_NETWORK_AVAILABLE
import com.gratus.core.util.CoreConstants.UIConstant.NETWORK_AVAILABLE_ACTION
import javax.inject.Inject

// Broadcast receiver which checks for network connection periodical
// notify in app the availability of network
class NetworkOnlineReceiver @Inject constructor(private val networkOnlineCheck: NetworkOnlineCheck) :
    BroadcastReceiver() {

    override fun onReceive(context: Context, arg1: Intent) {
        val networkStateIntent = Intent(NETWORK_AVAILABLE_ACTION)
        networkStateIntent.putExtra(IS_NETWORK_AVAILABLE, networkCheck())
        LocalBroadcastManager.getInstance(context).sendBroadcast(networkStateIntent)
    }

    private fun networkCheck(): Boolean {
        return networkOnlineCheck.isNetworkOnline
    }
}
