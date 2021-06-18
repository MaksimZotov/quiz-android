package com.maksimzotov.quiz.model.communication

import data.Data

interface Observer {
    fun getData(data: Data)
}

interface Observable {
    fun setObserver(observer: Observer)
}