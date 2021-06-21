package com.maksimzotov.quiz.viewmodel

import androidx.lifecycle.ViewModel
import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.model.communication.Observer
import com.maksimzotov.quiz.model.communication.SenderToServer
import com.maksimzotov.quiz.util.SingleLiveData
import data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class InvitationToPlayViewModel : ViewModel(), Observer {
    val data = SingleLiveData<Data>()

    val nameOfAnotherPlayer = AppState.nameOfAnotherPlayer

    fun acceptTheInvitation() {
        GlobalScope.launch(Dispatchers.IO) {
            SenderToServer.sendData(AcceptingTheInvitation(nameOfAnotherPlayer))
        }
    }

    fun refuseTheInvitation() {
        GlobalScope.launch(Dispatchers.IO) {
            SenderToServer.sendData(RefusalTheInvitation(nameOfAnotherPlayer))
        }
    }

    override fun getData(data: Data) {
        this.data.value = data
    }
}