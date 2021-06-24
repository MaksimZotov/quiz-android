package com.maksimzotov.quiz.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.maksimzotov.quiz.R
import com.maksimzotov.quiz.databinding.FragmentFinishGameBinding
import com.maksimzotov.quiz.databinding.FragmentGameBinding
import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.model.communication.ReceiverFromServer
import com.maksimzotov.quiz.view.base.BaseFragment
import com.maksimzotov.quiz.viewmodel.FinishGameViewModel
import com.maksimzotov.quiz.viewmodel.GameViewModel
import data.*

class FinishGameFragment :
        BaseFragment
            <FragmentFinishGameBinding, FinishGameViewModel>
                (FragmentFinishGameBinding::inflate) {

    override val viewModel: FinishGameViewModel by viewModels()

    override fun assignBinding(binding: FragmentFinishGameBinding) {
        with(binding) {
            viewModel = this@FinishGameFragment.viewModel
            chooseAnotherPlayerAfterFinish.setOnClickListener {
                onBackPressed()
            }
        }
    }

    override fun handleData(data: Data) {
        when (data) {
            is PlayTheGame -> {
                AppState.waitingForPlayTheGame = false
                findNavController().popBackStack()
            }
            is RefusalToPlayAgain -> {
                AppState.waitingForPlayTheGame = false
                Toast.makeText(activity, "Another player doesn't want to play again", Toast.LENGTH_SHORT).show()
                val navController = findNavController()
                navController.popBackStack()
                navController.popBackStack()
            }
            is HardRemovalOfThePlayer -> {
                AppState.waitingForPlayTheGame = false
                Toast.makeText(activity, "Unknown error on the side of another player", Toast.LENGTH_SHORT).show()
                val navController = findNavController()
                navController.popBackStack()
                navController.popBackStack()
            }
            else -> {
                throw Exception("Incorrect data for the FinishGame fragment")
            }
        }
    }

    override fun onBackPressed() {
        viewModel.chooseAnotherPlayer()
        val navController = findNavController()
        navController.popBackStack()
        navController.popBackStack()
    }
}