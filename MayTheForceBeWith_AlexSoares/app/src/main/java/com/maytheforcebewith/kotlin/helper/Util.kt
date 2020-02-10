package com.maytheforcebewith.kotlin.helper

import android.content.Context
import android.net.ConnectivityManager

object Util {
    fun isConnectedFast(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info != null && info.isConnected
    }
}