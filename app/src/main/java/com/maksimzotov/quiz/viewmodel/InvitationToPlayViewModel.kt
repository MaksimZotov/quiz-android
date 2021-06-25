package com.maksimzotov.quiz.viewmodel

import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.model.communication.SenderToServer
import com.maksimzotov.quiz.viewmodel.base.BaseViewModel
import data.*

class InvitationToPlayViewModel : BaseViewModel() {
    fun acceptTheInvitation() {
        if (!AppState.waitingForPlayTheGame) {
            AppState.waitingForPlayTheGame = true
            SenderToServer.sendData(AcceptingTheInvitation(AppState.nameOfAnotherPlayer))
        }
    }

    fun notifyThatResponseToAcceptingTheInvitationWasReceived() {
        AppState.waitingForPlayTheGame = false
    }

    fun refuseTheInvitation() {
        SenderToServer.sendData(RefusalTheInvitation(AppState.nameOfAnotherPlayer))
    }
}