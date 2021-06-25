package com.maksimzotov.quiz.view

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.maksimzotov.quiz.R
import com.maksimzotov.quiz.databinding.FragmentInvitationToPlayBinding
import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.util.shortToast
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
                viewModel!!.refuseTheInvitation()
                findNavController().popBackStack(R.id.searchOnNameFragment, false)
            }
            doYouWantToPlay.text = getString(
                    R.string.do_you_want_to_play_with_the_player_P1,
                    viewModel!!.nameOfAnotherPlayer
            )
        }
    }

    override fun handleData(data: Data) {
        if (
                data is PlayTheGame ||
                data is ThePlayerWhoInvitedYouIsWaitingForAcceptingTheInvitationFromAnotherPlayer ||
                data is IncorrectAcceptingTheInvitation || data is IncorrectRefusalTheInvitation ||
                data is HardRemovalOfThePlayer || data is IncorrectRefusalTheInvitation
        ) {
            viewModel.notifyThatResponseToAcceptingTheInvitationWasReceived()
        }
        when (data) {
            is PlayTheGame -> {
                findNavController().navigate(R.id.gameFragment)
            }
            is ThePlayerWhoInvitedYouIsWaitingForAcceptingTheInvitationFromAnotherPlayer -> {
                shortToast(
                        activity,
                        getString(
                                R.string.the_player_P1_is_waiting_for_accepting_the_invitation,
                                data.name
                        )
                )
                findNavController().popBackStack(R.id.searchOnNameFragment, false)
            }
            is IncorrectAcceptingTheInvitation -> {
                shortToast(
                        activity,
                        getString(R.string.the_player_P1_does_not_exist, data.name)
                )
                findNavController().popBackStack(R.id.searchOnNameFragment, false)
            }
            is HardRemovalOfThePlayer -> {
                shortToast(
                        activity,
                        getString(R.string.unknown_error_on_the_side_of_another_player)
                )
                findNavController().popBackStack(R.id.searchOnNameFragment, false)
            }
        }
    }

    override fun onBackPressed() {
        viewModel.refuseTheInvitation()
        findNavController().popBackStack(R.id.searchOnNameFragment, false)
    }
}