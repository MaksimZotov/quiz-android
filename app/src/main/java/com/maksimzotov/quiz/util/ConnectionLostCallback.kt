package com.maksimzotov.quiz.util

import android.net.ConnectivityManager
import android.net.Network

abstract class ConnectionLostCallback : ConnectivityManager.NetworkCallback() {
    abstract fun notifyThatConnectionLost()

    override fun onLost(network: Network) {
        notifyThatConnectionLost()
    }
}