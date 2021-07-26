package com.maksimzotov.quiz.viewmodel

import androidx.lifecycle.MutableLiveData
import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.model.communication.SenderToServer
import com.maksimzotov.quiz.viewmodel.base.BaseViewModel
import data.*

class GameViewModel : BaseViewModel() {
    val nameOfAnotherPlayer get() = AppState.nameOfAnotherPlayer

    val question: MutableLiveData<String> = MutableLiveData("")
    val firstAnswer: MutableLiveData<String> = MutableLiveData("")
    val secondAnswer: MutableLiveData<String> = MutableLiveData("")
    val thirdAnswer: MutableLiveData<String> = MutableLiveData("")
    val remainingTime: MutableLiveData<String> = MutableLiveData("")
    val score: MutableLiveData<String> = MutableLiveData("0/0")

    var isAbleToGiveAnswer = false
    var answerIsSelected = false

    private var indexOfAnswer = -1
    private var indexOfCorrectAnswer = -2

    private var playerScore = 0
    private var scoreOfAnotherPlayer = 0

    fun setAnswer(indexOfAnswer: Int) {
        this.indexOfAnswer = indexOfAnswer
        answerIsSelected = true
    }

    fun onStart() {
        question.value = ""
        firstAnswer.value = ""
        secondAnswer.value = ""
        thirdAnswer.value = ""
        remainingTime.value = ""
        score.value = "0/0"
    }

    fun giveAnswer(): Boolean {
        if (!isAbleToGiveAnswer) {
            return false
        }
        isAbleToGiveAnswer = false
        SenderToServer.sendData(Answer(indexOfAnswer))
        return indexOfAnswer == indexOfCorrectAnswer
    }

    fun leaveGame() {
        SenderToServer.sendData(LeavingTheGame())
    }

    fun saveGameState() {
        AppState.playerScore = playerScore
        AppState.scoreOfAnotherPlayer = scoreOfAnotherPlayer
    }

    override fun getData(data: Data) {
        when (data) {
            is Question -> {
                question.value = data.question
                firstAnswer.value = data.answers[0]
                secondAnswer.value = data.answers[1]
                thirdAnswer.value = data.answers[2]
                indexOfCorrectAnswer = data.indexOfCorrectAnswer
                isAbleToGiveAnswer = true
                answerIsSelected = false
                this.data.value = data
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

                playerScore = data.playerNameToScore[AppState.playerName]!!
                scoreOfAnotherPlayer = data.playerNameToScore[AppState.nameOfAnotherPlayer]!!

                score.value = "$playerScore/$scoreOfAnotherPlayer"
            }
            else -> {
                this.data.value = data
            }
        }
    }
}