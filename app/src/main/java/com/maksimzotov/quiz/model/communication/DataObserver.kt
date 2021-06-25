package com.maksimzotov.quiz.model.communication

import data.Data

interface DataObserver {
    fun subscribeOnObservableData()
    fun getData(data: Data)
}

interface DataObservable {
    fun setObserver(dataObserver: DataObserver)
}