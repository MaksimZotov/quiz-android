package com.maksimzotov.quiz.viewmodel

import androidx.lifecycle.ViewModel
import com.maksimzotov.quiz.util.SingleLiveData
import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.model.communication.Observer
import com.maksimzotov.quiz.model.communication.SenderToServer
import data.Data
import data.Name
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AuthenticationViewModel : ViewModel(), Observer {
    val data = SingleLiveData<Data>()

    var playerName = ""

    fun sendPlayerName() {
        AppState.playerName = playerName
        GlobalScope.launch(Dispatchers.IO) {
            SenderToServer.createConnection()
            SenderToServer.sendData(Name(AppState.playerName))
        }
    }

    override fun getData(data: Data) {
        this.data.value = data
    }
}