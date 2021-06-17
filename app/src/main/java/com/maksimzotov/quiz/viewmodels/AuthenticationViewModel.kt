package com.maksimzotov.quiz.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maksimzotov.quiz.R
import com.maksimzotov.quiz.model.Observer
import com.maksimzotov.quiz.model.ReceiverFromServer
import com.maksimzotov.quiz.model.SenderToServer
import data.AcceptingTheName
import data.Data
import data.Name
import data.RefusalTheName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AuthenticationViewModel : ViewModel(), Observer {
    val toastShort: MutableLiveData<String> = MutableLiveData()
    val goToFragment: MutableLiveData<Int> = MutableLiveData()

    var playerName = ""

    init {
        ReceiverFromServer.setObserver(this)
    }

    fun setPlayerName() {
        GlobalScope.launch(Dispatchers.IO) {
            SenderToServer.createConnection()
            SenderToServer.sendData(Name(playerName))
        }
    }

    override fun getData(data: Data) {
        when (data) {
            is RefusalTheName -> {
                toastShort.value = "The name ${data.name} is taken"
            }
            is AcceptingTheName -> {
                goToFragment.value = R.id.searchOnNameFragment
            }
            else -> {
                throw Exception("Incorrect data for the Authentication fragment")
            }
        }
    }
}