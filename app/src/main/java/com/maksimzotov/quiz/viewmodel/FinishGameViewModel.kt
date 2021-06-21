package com.maksimzotov.quiz.viewmodel

import androidx.lifecycle.ViewModel
import com.maksimzotov.quiz.model.communication.Observer
import com.maksimzotov.quiz.model.communication.SenderToServer
import com.maksimzotov.quiz.util.SingleLiveData
import data.Data
import data.RefusalToPlayAgain
import data.RequestToPlayAgain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FinishGameViewModel : ViewModel(), Observer {
    val data = SingleLiveData<Data>()

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
        this.data.value = data
    }
}