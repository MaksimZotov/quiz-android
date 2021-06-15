package com.maksimzotov.quiz.viewmodels.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maksimzotov.quiz.model.Observer
import com.maksimzotov.quiz.model.SenderToServer
import data.Data
import data.RefusalToPlayAgain
import data.PlayTheGame
import data.RequestToPlayAgain

class FinishGameViewModel : ViewModel(), Observer {
    val toastShort: MutableLiveData<String> = MutableLiveData()
    lateinit var popBackStack: () -> Unit

    fun chooseAnotherPlayer() {
        SenderToServer.sendData(RefusalToPlayAgain())
        popBackStack()
    }

    fun playAgain() {
        SenderToServer.sendData(RequestToPlayAgain())
    }

    override fun getData(data: Data) {
        when (data) {
            is PlayTheGame -> {
                popBackStack()
            }
            is RefusalToPlayAgain -> {
                toastShort.value = "Another player doesn't want to play again"
                popBackStack()
                popBackStack()
            }
        }
    }
}