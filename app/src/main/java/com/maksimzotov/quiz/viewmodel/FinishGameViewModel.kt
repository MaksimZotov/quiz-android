package com.maksimzotov.quiz.viewmodel

import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.model.communication.SenderToServer
import com.maksimzotov.quiz.viewmodel.base.BaseViewModel
import data.RefusalToPlayAgain
import data.RequestToPlayAgain

class FinishGameViewModel : BaseViewModel() {
    val playerScore get() = AppState.playerScore
    val scoreOfAnotherPlayer get() = AppState.scoreOfAnotherPlayer
    val nameOfAnotherPlayer get() = AppState.nameOfAnotherPlayer

    fun chooseAnotherPlayer() {
        SenderToServer.sendData(RefusalToPlayAgain())
    }

    fun playAgain() {
        if (!AppState.waitingForPlayTheGame) {
            AppState.waitingForPlayTheGame = true
            SenderToServer.sendData(RequestToPlayAgain())
        }
    }

    fun notifyThatResponseToRequestToPlayAgainWasReceived() {
        AppState.waitingForPlayTheGame = false
    }
}