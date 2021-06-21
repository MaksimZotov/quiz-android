package com.maksimzotov.quiz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maksimzotov.quiz.model.communication.Observer
import com.maksimzotov.quiz.model.communication.SenderToServer
import com.maksimzotov.quiz.util.SingleLiveData
import data.*

class GameViewModel : ViewModel(), Observer {
    val data = SingleLiveData<Data>()

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
                score.value = "${data.player}/${data.anotherPlayer}"
            }
            else -> {
                this.data.value = data
            }
        }
    }
}