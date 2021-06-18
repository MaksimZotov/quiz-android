package com.maksimzotov.quiz.model.communication

import com.maksimzotov.quiz.model.network.Server
import data.Data

object SenderToServer {
    private val server = Server()

    fun createConnection() {
        server.createConnection()
    }

    fun sendData(data: Data) {
        server.sendDataToServer(data)
    }

    fun closeConnection() {
        server.closeConnection()
    }
}