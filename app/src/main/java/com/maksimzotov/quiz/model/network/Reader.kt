package com.maksimzotov.quiz.model.network

import data.Data
import java.io.IOException
import java.io.ObjectInputStream

class Reader(private val server: Server, private val inputStream: ObjectInputStream): Thread() {

    init {
        start()
    }

    override fun run() {
        try {
            while (true) {
                val data = inputStream.readObject() as Data
                server.handleDataFromServer(data)
            }
        } catch (ex: IOException) { }
    }
}