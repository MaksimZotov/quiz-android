package com.maksimzotov.quiz.model.communication

import android.os.Handler
import android.os.Looper
import data.Data
import data.Ping
import data.Pong

object ReceiverFromServer : DataObservable {
    private lateinit var currentDataObserver: DataObserver

    private val handler = Handler(Looper.getMainLooper())

    override fun setObserver(dataObserver: DataObserver) {
        currentDataObserver = dataObserver
    }

    fun getData(data: Data) {
        if (data is Ping) {
            SenderToServer.sendData(Pong())
        }
        else {
            handler.post {
                currentDataObserver.getData(data)
            }
        }
    }
}