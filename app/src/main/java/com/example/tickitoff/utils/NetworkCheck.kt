package com.example.tickitoff.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

// Method that checks whether user is connected to internet, will be used as a check before opening links

fun isConnectedToInternet(context: Context): Boolean { // returns boolean of true/false
    val connectionManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectionManager.getNetworkCapabilities(connectionManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) { // Connected with cellular/mobile data
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) { // Connected with wifi
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) { // Connected with ethernet
            return true
        }
    }
    return false
}