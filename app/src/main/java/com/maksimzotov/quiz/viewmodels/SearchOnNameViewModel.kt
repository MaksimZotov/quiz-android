package com.maksimzotov.quiz.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maksimzotov.quiz.R
import com.maksimzotov.quiz.SingleLiveData
import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.model.communication.Observer
import com.maksimzotov.quiz.model.communication.ReceiverFromServer
import com.maksimzotov.quiz.model.communication.SenderToServer
import data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchOnNameViewModel : ViewModel(), Observer {
    private val _data: SingleLiveData<Data> = SingleLiveData()
    val data = this._data

    var nameOfAnotherPlayer = ""

    fun handleInvitation(name: String) {
        AppState.nameOfAnotherPlayer = name
    }

    fun inviteAnotherPlayer() {
        AppState.nameOfAnotherPlayer = nameOfAnotherPlayer
        GlobalScope.launch(Dispatchers.IO) {
            SenderToServer.sendData(Invitation(AppState.nameOfAnotherPlayer))
        }
    }

    override fun getData(data: Data) {
        _data.value = data
    }
}