package com.gratus.core.util

object CoreConstants {
    // base url
    object RemoteConstant {
        const val BASE_URL = "https://fake-poi-api.mytaxi.com"
        const val DIRECTION_BASE_URL = "https://api.openrouteservice.org"
        const val P1LAT = 53.694865
        const val P1LONG = 9.757589
        const val P2LAT = 53.394655
        const val P2LONG = 10.099891
    }

    object DatabaseConstant {
        // database
        const val FREE_NOW_DB = "free_now_database"
        const val USER_TABLE = "user_table"
    }

    object PrefConstant {
        //  tag for shared preference constants
        const val APP_PREF_NAME = "FREE NOW"
        const val USERNAME = "username"
        const val PASSWORD = "password"
    }

    object CommonConstant {
        const val CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE"
    }

    object UIConstant {
        const val NETWORK_AVAILABLE_ACTION = "NW AVAILABLE"
        const val IS_NETWORK_AVAILABLE = "isNetworkAvailable"
        const val LOCATION_PERMISSION_REQUEST_CODE = 999
        const val TAXI = "TAXI"
        val traffic = arrayOf("Low", "Moderate", "High")
        const val POOLING = "POOLING"
        const val SELECTED = "SELECTED"
        const val POLYLINE = "POLYLINE"
    }
}
