package com.maksimzotov.quiz.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.maksimzotov.quiz.R
import com.maksimzotov.quiz.viewmodels.fragments.FinishGameViewModel

class FinishGameFragment : Fragment() {
    private val viewModel: FinishGameViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_finish_game, container, false)

        val toastShort = Observer<String> { message ->  Toast.makeText(activity, message, Toast.LENGTH_SHORT).show() }
        viewModel.toastShort.observe(viewLifecycleOwner, toastShort)

        viewModel.popBackStack = { findNavController().popBackStack() }

        val buttonChooseAnotherPlayer: Button = view.findViewById(R.id.chooseAnotherPlayerAfterFinish) ?: throw Exception("Incorrect ID")
        buttonChooseAnotherPlayer.setOnClickListener { viewModel.chooseAnotherPlayer() }

        val buttonPlayAgain: Button = view.findViewById(R.id.playAgain) ?: throw Exception("Incorrect ID")
        buttonPlayAgain.setOnClickListener { viewModel.playAgain() }

        return view
    }
}