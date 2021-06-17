package com.maksimzotov.quiz.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maksimzotov.quiz.R
import com.maksimzotov.quiz.model.Observer
import com.maksimzotov.quiz.model.SenderToServer
import data.*

class SearchOnNameViewModel : ViewModel(), Observer {
    val toastShort: MutableLiveData<String> = MutableLiveData()
    val goToFragment: MutableLiveData<Int> = MutableLiveData()

    var nameOfAnotherPlayer = ""

    fun inviteAnotherPlayer() = SenderToServer.sendData(Invitation(nameOfAnotherPlayer))

    override fun getData(data: Data) {
        when (data) {
            is PlayTheGame -> {
                goToFragment.value = R.id.gameFragment
            }
            is RefusalTheInvitation -> {
                toastShort.value = "The player ${data.name} has refused the invitation"
            }
            else -> {
                throw Exception("Incorrect data for the SearchOnName fragment")
            }
        }
    }
}