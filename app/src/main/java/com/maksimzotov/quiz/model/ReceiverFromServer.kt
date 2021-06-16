package com.maksimzotov.quiz.model

import android.os.Handler
import android.os.Looper
import android.os.Message
import data.Data

object ReceiverFromServer : Observable {
    private lateinit var currentObserver: Observer
    private val CODE_RECEIVED_DATA_FROM_SERVER = 1

    private val handler = object : Handler(Looper.myLooper()!!) {
        override fun handleMessage(msg: Message) {
            if (msg.what == CODE_RECEIVED_DATA_FROM_SERVER) {
                val data = msg.obj as Data
                currentObserver.getData(data)
            }
        }
    }

    override fun setObserver(observer: Observer) {
        currentObserver = observer
    }

    fun getData(data: Data) {
        handler.sendMessage(handler.obtainMessage(CODE_RECEIVED_DATA_FROM_SERVER, -1, -1, data))
    }
}