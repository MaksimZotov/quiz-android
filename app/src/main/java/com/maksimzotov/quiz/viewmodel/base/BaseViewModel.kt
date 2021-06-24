package com.maksimzotov.quiz.viewmodel.base

import androidx.lifecycle.ViewModel
import com.maksimzotov.quiz.model.communication.Observer
import com.maksimzotov.quiz.util.SingleLiveData
import data.Data

open class BaseViewModel: ViewModel(), Observer {
    val data = SingleLiveData<Data>()

    override fun getData(data: Data) {
        this.data.value = data
    }
}