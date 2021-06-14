package com.maksimzotov.quiz.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maksimzotov.quiz.R
import com.maksimzotov.quiz.model.Observer
import com.maksimzotov.quiz.model.Server
import data.Data

class GameFragment : Fragment(), Observer {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        Server.setObserver(this)
        return view
    }

    override fun getData(data: Data) {
        TODO()
    }
}