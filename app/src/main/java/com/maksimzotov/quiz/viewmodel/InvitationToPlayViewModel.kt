package com.maksimzotov.quiz.viewmodel

import androidx.lifecycle.ViewModel
import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.model.communication.Observer
import com.maksimzotov.quiz.model.communication.SenderToServer
import com.maksimzotov.quiz.util.SingleLiveData
import data.*

class InvitationToPlayViewModel : ViewModel(), Observer {
    val data = SingleLiveData<Data>()

    fun acceptTheInvitation() {
        if (!AppState.waitingForPlayTheGame) {
            AppState.waitingForPlayTheGame = true
            SenderToServer.sendData(AcceptingTheInvitation(AppState.nameOfAnotherPlayer))
        }
    }

    fun refuseTheInvitation() {
        SenderToServer.sendData(RefusalTheInvitation(AppState.nameOfAnotherPlayer))
    }

    override fun getData(data: Data) {
        this.data.value = data
    }
}