package com.maksimzotov.quiz.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maksimzotov.quiz.R
import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.model.communication.Observer
import com.maksimzotov.quiz.model.communication.ReceiverFromServer
import com.maksimzotov.quiz.model.communication.SenderToServer
import data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameViewModel : ViewModel(), Observer {
    private val _data: MutableLiveData<Data> = MutableLiveData()
    val data: LiveData<Data> = _data

    private var indexOfAnswer = -1

    fun setAnswer(indexOfAnswer: Int) {
        this.indexOfAnswer = indexOfAnswer
    }

    fun giveAnswer() {
        GlobalScope.launch(Dispatchers.IO) {
            SenderToServer.sendData(Answer(indexOfAnswer))
        }
    }

    fun leaveGame() {
        GlobalScope.launch(Dispatchers.IO) {
            SenderToServer.sendData(LeavingTheGame())
        }
    }

    val toastShort: MutableLiveData<String> = MutableLiveData()
    val goToFragment: MutableLiveData<Int> = MutableLiveData()

    val question: MutableLiveData<String> = MutableLiveData()
    val firstAnswer: MutableLiveData<String> = MutableLiveData()
    val secondAnswer: MutableLiveData<String> = MutableLiveData()
    val thirdAnswer: MutableLiveData<String> = MutableLiveData()

    val remainingTime: MutableLiveData<Double> = MutableLiveData()

    val scorePair: MutableLiveData<Pair<Int, Int>> = MutableLiveData()

    private var indexOfCorrectAnswer = -2

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
                remainingTime.value = data.time
            }
            is Score -> {
                scorePair.value = data.player to data.anotherPlayer
            }
            is FinishTheGame -> {
                goToFragment.value = R.id.finishGameFragment
            }
            else -> {
                _data.value = data
            }
        }
    }
}