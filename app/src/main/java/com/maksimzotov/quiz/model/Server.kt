package com.maksimzotov.quiz.model

object Server: Observable {
    private lateinit var currentObserver: Observer

    override fun setObserver(observer: Observer) {
        currentObserver = observer
    }
}