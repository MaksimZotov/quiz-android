package com.maksimzotov.quiz.viewmodel.base

import androidx.lifecycle.ViewModel
import com.maksimzotov.quiz.model.communication.DataObserver
import com.maksimzotov.quiz.model.communication.ReceiverFromServer
import com.maksimzotov.quiz.model.communication.SenderToServer
import com.maksimzotov.quiz.util.SingleLiveData
import data.Data

open class BaseViewModel: ViewModel(), DataObserver {
    val data = SingleLiveData<Data>()

    fun closeConnection() {
        SenderToServer.closeConnection()
    }

    override fun subscribeOnObservableData() {
        ReceiverFromServer.setObserver(this)
    }

    override fun getData(data: Data) {
        this.data.value = data
    }
}