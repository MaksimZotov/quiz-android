package com.maksimzotov.quiz.model.network

import data.Data
import java.io.ObjectOutputStream

class Sender(private val outputStream: ObjectOutputStream) {

    fun sendDataToServer(data: Data) {
        outputStream.writeObject(data)
        outputStream.flush()
    }
}