package com.maksimzotov.quiz.model.communication

import com.maksimzotov.quiz.model.network.Server
import data.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object SenderToServer {
    private val server = Server()
    private var playerIsConnected = false

    fun sendData(data: Data) {
        GlobalScope.launch(Dispatchers.IO) {
            if (!playerIsConnected) {
                server.createConnection()
                playerIsConnected = true
            }
            server.sendDataToServer(data)
        }
    }

    fun closeConnection() {
        GlobalScope.launch(Dispatchers.IO) {
            server.closeConnection()
            playerIsConnected = false
        }
    }
}