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
import com.maksimzotov.quiz.databinding.FragmentInvitationToPlayBinding
import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.model.communication.ReceiverFromServer
import com.maksimzotov.quiz.view.base.BaseFragment
import com.maksimzotov.quiz.viewmodel.GameViewModel
import com.maksimzotov.quiz.viewmodel.InvitationToPlayViewModel
import data.*

class GameFragment :
        BaseFragment
            <FragmentGameBinding, GameViewModel>
                (FragmentGameBinding::inflate) {

    override val viewModel: GameViewModel by viewModels()

    override fun assignBinding(binding: FragmentGameBinding) {
        with(binding) {
            viewModel = this@GameFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun handleData(data: Data) {
        when (data) {
            is LeavingTheGame -> {
                Toast.makeText(activity, "The player \"${AppState.nameOfAnotherPlayer}\" has left the game", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
            is FinishTheGame -> {
                findNavController().navigate(R.id.finishGameFragment)
            }
            is HardRemovalOfThePlayer -> {
                Toast.makeText(activity, "Unknown error on the side of another player", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
            else -> {
                throw Exception("Incorrect data for the Game fragment")
            }
        }
    }

    override fun onBackPressed() {
        viewModel.leaveGame()
        findNavController().popBackStack()
    }
}