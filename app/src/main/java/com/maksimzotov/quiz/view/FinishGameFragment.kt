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
import com.maksimzotov.quiz.util.shortToast
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
                findNavController().popBackStack(R.id.gameFragment, false)
            }
            is RefusalToPlayAgain -> {
                AppState.waitingForPlayTheGame = false
                shortToast(
                        activity,
                        getString(R.string.another_player_does_not_want_to_play)
                )
                findNavController().popBackStack(R.id.searchOnNameFragment, false)

            }
            is HardRemovalOfThePlayer -> {
                AppState.waitingForPlayTheGame = false
                shortToast(
                        activity,
                        getString(R.string.unknown_error_on_the_side_of_another_player)
                )
                findNavController().popBackStack(R.id.searchOnNameFragment, false)
            }
            else -> {
                throw Exception("Incorrect data for the FinishGame fragment")
            }
        }
    }

    override fun onBackPressed() {
        viewModel.chooseAnotherPlayer()
        findNavController().popBackStack(R.id.searchOnNameFragment, false)
    }
}