package com.maksimzotov.quiz.model.network

import data.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.ObjectInputStream

class Reader(private val server: Server, private val inputStream: ObjectInputStream) {

    init {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                while (true) {
                    val data = inputStream.readObject() as Data
                    server.handleDataFromServer(data)
                }
            } catch (ex: IOException) { }
        }
    }
}