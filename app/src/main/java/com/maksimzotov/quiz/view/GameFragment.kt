package com.maksimzotov.quiz.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.maksimzotov.quiz.R
import com.maksimzotov.quiz.databinding.FragmentGameBinding
import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.model.communication.ReceiverFromServer
import com.maksimzotov.quiz.viewmodels.GameViewModel
import data.*

class GameFragment : Fragment() {
    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        observeDataFromServer()
        val binding: FragmentGameBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game, container, false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.leaveGame()
                findNavController().navigate(R.id.searchOnNameFragment)
            }
        })

        return binding.root
    }

    private fun observeDataFromServer() {
        ReceiverFromServer.setObserver(viewModel)
        val dataObserver = Observer<Data> { data ->
            when (data) {
                is LeavingTheGame -> {
                    Toast.makeText(activity, "The player \"${AppState.nameOfAnotherPlayer}\" has left the game", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.searchOnNameFragment)
                }
                is FinishTheGame -> {
                    findNavController().navigate(R.id.finishGameFragment)
                }
                else -> {
                    throw Exception("Incorrect data for the Game fragment")
                }
            }
        }
        viewModel.data.observe(viewLifecycleOwner, dataObserver)
    }
}