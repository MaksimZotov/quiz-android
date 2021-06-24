package com.maksimzotov.quiz.viewmodel

import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.model.communication.SenderToServer
import com.maksimzotov.quiz.viewmodel.base.BaseViewModel
import data.Name

class AuthenticationViewModel : BaseViewModel() {
    var playerName = ""

    fun sendPlayerName() {
        if (!AppState.waitingForAcceptingTheName) {
            AppState.waitingForAcceptingTheName = true
            AppState.playerName = playerName
            SenderToServer.sendData(Name(AppState.playerName))
        }
    }
}