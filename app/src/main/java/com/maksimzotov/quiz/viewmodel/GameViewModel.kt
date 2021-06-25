package com.maksimzotov.quiz.viewmodel

import androidx.lifecycle.MutableLiveData
import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.model.communication.SenderToServer
import com.maksimzotov.quiz.viewmodel.base.BaseViewModel
import data.*

class GameViewModel : BaseViewModel() {
    val question: MutableLiveData<String> = MutableLiveData("question")
    val firstAnswer: MutableLiveData<String> = MutableLiveData("firstAnswer")
    val secondAnswer: MutableLiveData<String> = MutableLiveData("secondAnswer")
    val thirdAnswer: MutableLiveData<String> = MutableLiveData("thirdAnswer")
    val remainingTime: MutableLiveData<String> = MutableLiveData("0")
    val score: MutableLiveData<String> = MutableLiveData("0/0")

    private var indexOfAnswer = -1
    private var indexOfCorrectAnswer = -2

    fun setAnswer(indexOfAnswer: Int) {
        this.indexOfAnswer = indexOfAnswer
    }

    fun giveAnswer() {
        SenderToServer.sendData(Answer(indexOfAnswer))
    }

    fun leaveGame() {
        SenderToServer.sendData(LeavingTheGame())
    }

    override fun getData(data: Data) {
        when (data) {
            is Question -> {
                question.value = data.question
                firstAnswer.value = data.answers[0]
                secondAnswer.value = data.answers[1]
                thirdAnswer.value = data.answers[2]
                indexOfCorrectAnswer = data.indexOfCorrectAnswer
            }
            is RemainingTime -> {
                remainingTime.value = data.time.toString()
            }
            is Score -> {
                check(
                        data.playerNameToScore.contains(AppState.playerName) &&
                        data.playerNameToScore.contains(AppState.nameOfAnotherPlayer) &&
                        data.playerNameToScore.size == 2
                )

                val scorePlayer = data.playerNameToScore[AppState.playerName]
                val scoreAnotherPlayer = data.playerNameToScore[AppState.nameOfAnotherPlayer]

                score.value = "$scorePlayer/$scoreAnotherPlayer"
            }
            else -> {
                this.data.value = data
            }
        }
    }
}