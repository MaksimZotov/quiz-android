package com.maksimzotov.quiz.viewmodels

import androidx.lifecycle.ViewModel
import com.maksimzotov.quiz.model.Observer
import com.maksimzotov.quiz.model.SenderToServer
import data.AcceptingTheInvitation
import data.Data
import data.Invitation
import data.RefusalTheInvitation

class InvitationToPlayViewModel : ViewModel(), Observer {
    lateinit var nameOfAnotherPlayer: String

    fun acceptTheInvitation() {
        SenderToServer.sendData(AcceptingTheInvitation(nameOfAnotherPlayer))
    }

    fun refuseTheInvitation() {
        SenderToServer.sendData(RefusalTheInvitation(nameOfAnotherPlayer))
    }

    override fun getData(data: Data) {
        when (data) {
            is Invitation -> {
                nameOfAnotherPlayer = data.name
            }
        }
    }
}