package com.maksimzotov.quiz.model.network

import com.maksimzotov.quiz.model.communication.ReceiverFromServer
import data.Data
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket

class Server {
    private lateinit var clientSocket: Socket
    private lateinit var output: ObjectOutputStream
    private lateinit var input: ObjectInputStream
    private lateinit var sender: Sender
    private lateinit var reader: Reader

    fun createConnection() {
        clientSocket = Socket("34.116.177.225", 80)
        output = ObjectOutputStream(clientSocket.getOutputStream())
        input = ObjectInputStream(clientSocket.getInputStream())
        sender = Sender(output)
        reader = Reader(this, input)
    }

    fun closeConnection() {
        clientSocket.close()
        input.close()
        output.close()
    }

    fun sendDataToServer(data: Data) {
        sender.sendDataToServer(data)
    }

    fun handleDataFromServer(data: Data) {
        ReceiverFromServer.getData(data)
    }
}