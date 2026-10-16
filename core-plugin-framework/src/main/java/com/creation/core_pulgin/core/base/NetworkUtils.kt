package com.creation.core_pulgin.core.base

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity

class NetworkUtils {

    companion object {
        const val NO_NETWORK_MESSAGE =
            "No Internet Access! Please check your network settings and try again."

        fun isDeviceOnline(context: Context): Boolean {
            val connMgr =
                context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connMgr.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

}