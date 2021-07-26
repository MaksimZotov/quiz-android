package com.maksimzotov.quiz.model.communication

import data.Data

interface DataObserver {
    fun subscribeOnDataObservable()
    fun getData(data: Data)
}

interface DataObservable {
    fun setObserver(dataObserver: DataObserver)
}