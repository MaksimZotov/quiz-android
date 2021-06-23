package com.maksimzotov.quiz.model.communication

import com.maksimzotov.quiz.model.network.Server
import data.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

object SenderToServer {
    private val server = Server()

    fun sendData(data: Data) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                if (server.isClosed) {
                    server.createConnection()
                }
                server.sendDataToServer(data)
            } catch (ex: IOException) { }
        }
    }

    fun closeConnection() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                if (!server.isClosed) {
                    server.closeConnection()
                }
            } catch (ex: IOException) { }
        }
    }
}