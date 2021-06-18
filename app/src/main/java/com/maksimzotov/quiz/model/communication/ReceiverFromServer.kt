package com.maksimzotov.quiz.model.communication

import android.os.Handler
import android.os.Looper
import android.os.Message
import data.Data

object ReceiverFromServer : Observable {
    private lateinit var currentObserver: Observer

    private val handler = Handler(Looper.myLooper()!!)

    override fun setObserver(observer: Observer) {
        currentObserver = observer
    }

    fun getData(data: Data) {
        handler.post {
            currentObserver.getData(data)
        }
    }
}