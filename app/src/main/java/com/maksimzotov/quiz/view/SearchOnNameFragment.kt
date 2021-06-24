package com.maksimzotov.quiz.view

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.maksimzotov.quiz.R
import com.maksimzotov.quiz.databinding.FragmentSearchOnNameBinding
import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.view.base.BaseFragment
import com.maksimzotov.quiz.viewmodel.SearchOnNameViewModel
import data.*

class SearchOnNameFragment :
        BaseFragment
            <FragmentSearchOnNameBinding, SearchOnNameViewModel>
                (FragmentSearchOnNameBinding::inflate) {

    override val viewModel: SearchOnNameViewModel by viewModels()

    override fun assignBinding(binding: FragmentSearchOnNameBinding) {
        with(binding) {
            viewModel = this@SearchOnNameFragment.viewModel
        }
    }

    override fun handleData(data: Data) {
        when (data) {
            is Invitation -> {
                AppState.waitingForPlayTheGame = false
                viewModel.handleInvitation(data.name)
                findNavController().navigate(R.id.invitationToPlayFragment)
            }
            is PlayTheGame -> {
                AppState.waitingForPlayTheGame = false
                findNavController().navigate(R.id.gameFragment)
            }
            is RefusalTheInvitation -> {
                AppState.waitingForPlayTheGame = false
                Toast.makeText(activity, "The player \"${data.name}\" has refused the invitation", Toast.LENGTH_SHORT).show()
            }
            is IncorrectInvitation -> {
                AppState.waitingForPlayTheGame = false
                Toast.makeText(activity, "The player \"${data.name}\" does not exist", Toast.LENGTH_SHORT).show()
            }
            is InvitationMyself -> {
                AppState.waitingForPlayTheGame = false
                Toast.makeText(activity, "You can't invite yourself", Toast.LENGTH_SHORT).show()
            }
            is InvitedPlayerIsDecidingWhetherToPlayWithAnotherPlayer -> {
                AppState.waitingForPlayTheGame = false
                Toast.makeText(activity, "The invited player \"${data.name}\" is deciding whether to play with another player", Toast.LENGTH_SHORT).show()
            }
            is HardRemovalOfThePlayer -> {
                AppState.waitingForPlayTheGame = false
                Toast.makeText(activity, "Unknown error on the side of another player", Toast.LENGTH_SHORT).show()
            }
            else -> {
                throw Exception("Incorrect data for the SearchOnName fragment")
            }
        }
    }

    override fun onBackPressed() {
        viewModel.changeName()
        findNavController().popBackStack()
    }
}