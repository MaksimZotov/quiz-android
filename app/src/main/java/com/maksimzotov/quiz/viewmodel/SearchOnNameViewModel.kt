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
        if (!AppState.waitingForPlayTheGame) {
            AppState.waitingForPlayTheGame = true
            AppState.nameOfAnotherPlayer = nameOfAnotherPlayer
            SenderToServer.sendData(Invitation(AppState.nameOfAnotherPlayer))
        }
    }

    fun notifyThatResponseToInvitationWasReceived() {
        AppState.waitingForPlayTheGame = false
    }

    fun changeName() {
        SenderToServer.sendData(NameChange())
    }
}