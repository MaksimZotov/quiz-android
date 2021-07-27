package com.maksimzotov.quiz.model.network

import data.Data
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.ObjectOutputStream

class Sender(private val outputStream: ObjectOutputStream) {
    val mutex = Mutex()

    suspend fun sendDataToServer(data: Data) {
        mutex.withLock {
            outputStream.writeObject(data)
            outputStream.flush()
        }
    }
}