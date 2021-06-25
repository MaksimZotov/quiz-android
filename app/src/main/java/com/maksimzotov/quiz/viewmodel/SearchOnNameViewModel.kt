package com.maksimzotov.quiz.viewmodel

import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.model.communication.SenderToServer
import com.maksimzotov.quiz.viewmodel.base.BaseViewModel
import data.*

class SearchOnNameViewModel : BaseViewModel() {
    var nameOfAnotherPlayer = ""

    fun handleInvitation(name: String) {
        AppState.nameOfAnotherPlayer = name
    }

    fun inviteAnotherPlayer() {
        AppState.nameOfAnotherPlayer = nameOfAnotherPlayer
        SenderToServer.sendData(Invitation(AppState.nameOfAnotherPlayer))
    }

    fun changeName() {
        SenderToServer.sendData(NameChange())
    }
}