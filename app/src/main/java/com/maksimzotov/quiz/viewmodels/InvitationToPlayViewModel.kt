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

class InvitationToPlayViewModel : ViewModel(), Observer {
    private val _data: MutableLiveData<Data> = MutableLiveData()
    val data: LiveData<Data> = _data

    val nameOfAnotherPlayer = AppState.nameOfAnotherPlayer

    fun acceptTheInvitation() {
        GlobalScope.launch(Dispatchers.IO) {
            SenderToServer.sendData(AcceptingTheInvitation(nameOfAnotherPlayer))
        }
    }

    fun refuseTheInvitation() {
        GlobalScope.launch(Dispatchers.IO) {
            SenderToServer.sendData(RefusalTheInvitation(nameOfAnotherPlayer))
        }
    }

    override fun getData(data: Data) {
        _data.value = data
    }
}