package com.maksimzotov.quiz.viewmodels

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
    private val _data: SingleLiveData<Data> = SingleLiveData()
    val data = this._data

    var playerName = ""

    fun sendPlayerName() {
        AppState.playerName = playerName
        GlobalScope.launch(Dispatchers.IO) {
            SenderToServer.createConnection()
            SenderToServer.sendData(Name(AppState.playerName))
        }
    }

    override fun getData(data: Data) {
        _data.value = data
    }
}