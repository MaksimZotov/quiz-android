package com.maksimzotov.quiz.model.communication

import android.os.Handler
import android.os.Looper
import data.Data
import data.Ping
import data.Pong

object ReceiverFromServer : Observable {
    private lateinit var currentObserver: Observer

    private val handler = Handler(Looper.getMainLooper())

    override fun setObserver(observer: Observer) {
        currentObserver = observer
    }

    fun getData(data: Data) {
        if (data is Ping) {
            SenderToServer.sendData(Pong())
        }
        else {
            handler.post {
                currentObserver.getData(data)
            }
        }
    }
}