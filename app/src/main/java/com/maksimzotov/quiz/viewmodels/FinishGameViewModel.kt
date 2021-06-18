package com.maksimzotov.quiz.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maksimzotov.quiz.model.communication.Observer
import com.maksimzotov.quiz.model.communication.ReceiverFromServer
import com.maksimzotov.quiz.model.communication.SenderToServer
import data.Data
import data.RefusalToPlayAgain
import data.PlayTheGame
import data.RequestToPlayAgain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FinishGameViewModel : ViewModel(), Observer {
    private val _data: MutableLiveData<Data> = MutableLiveData()
    val data: LiveData<Data> = _data

    fun chooseAnotherPlayer() {
        GlobalScope.launch(Dispatchers.IO) {
            SenderToServer.sendData(RefusalToPlayAgain())
        }
    }

    fun playAgain() {
        GlobalScope.launch(Dispatchers.IO) {
            SenderToServer.sendData(RequestToPlayAgain())
        }
    }

    override fun getData(data: Data) {
        _data.value = data
    }
}