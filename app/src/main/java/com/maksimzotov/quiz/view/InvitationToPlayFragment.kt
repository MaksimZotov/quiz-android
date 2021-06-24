package com.maksimzotov.quiz.view

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.maksimzotov.quiz.R
import com.maksimzotov.quiz.databinding.FragmentInvitationToPlayBinding
import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.view.base.BaseFragment
import com.maksimzotov.quiz.viewmodel.InvitationToPlayViewModel
import data.*

class InvitationToPlayFragment :
        BaseFragment
            <FragmentInvitationToPlayBinding, InvitationToPlayViewModel>
                (FragmentInvitationToPlayBinding::inflate) {

    override val viewModel: InvitationToPlayViewModel by viewModels()

    override fun assignBinding(binding: FragmentInvitationToPlayBinding) {
        with(binding) {
            viewModel = this@InvitationToPlayFragment.viewModel
            doNotPlayWithAnotherPlayer.setOnClickListener {
                viewModel.refuseTheInvitation()
                findNavController().popBackStack()
            }
        }
    }

    override fun handleData(data: Data) {
        when (data) {
            is PlayTheGame -> {
                AppState.waitingForPlayTheGame = false
                val navController = findNavController()
                navController.popBackStack()
                navController.navigate(R.id.gameFragment)
            }
            is ThePlayerWhoInvitedYouIsWaitingForAcceptingTheInvitationFromAnotherPlayer -> {
                AppState.waitingForPlayTheGame = false
                Toast.makeText(activity, "The player \"${data.name}\" is waiting for accepting the invitation from another player", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
            is IncorrectAcceptingTheInvitation -> {
                AppState.waitingForPlayTheGame = false
                Toast.makeText(activity, "The player \"${data.name}\" does not exist", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
            is IncorrectRefusalTheInvitation -> {
                // do nothing
            }
            is HardRemovalOfThePlayer -> {
                AppState.waitingForPlayTheGame = false
                Toast.makeText(activity, "Unknown error on the side of another player", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
            else -> {
                throw Exception("Incorrect data for the SearchOnName fragment")
            }
        }
    }

    override fun onBackPressed() {
        viewModel.refuseTheInvitation()
        findNavController().popBackStack()
    }
}