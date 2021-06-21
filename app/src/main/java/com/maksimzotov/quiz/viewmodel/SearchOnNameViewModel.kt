package com.maksimzotov.quiz.viewmodel

import androidx.lifecycle.ViewModel
import com.maksimzotov.quiz.util.SingleLiveData
import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.model.communication.Observer
import com.maksimzotov.quiz.model.communication.SenderToServer
import data.*

class SearchOnNameViewModel : ViewModel(), Observer {
    val data = SingleLiveData<Data>()

    var nameOfAnotherPlayer = ""

    fun handleInvitation(name: String) {
        AppState.nameOfAnotherPlayer = name
    }

    fun inviteAnotherPlayer() {
        AppState.nameOfAnotherPlayer = nameOfAnotherPlayer
        SenderToServer.sendData(Invitation(AppState.nameOfAnotherPlayer))
    }

    override fun getData(data: Data) {
        this.data.value = data
    }
}