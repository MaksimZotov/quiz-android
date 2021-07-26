package com.maksimzotov.quiz.view

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.maksimzotov.quiz.R
import com.maksimzotov.quiz.databinding.FragmentInvitationToPlayBinding
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
        binding.also { b ->
            b.viewModel = viewModel
            b.doNotPlayWithAnotherPlayer.setOnClickListener {
                viewModel.refuseTheInvitation()
                findNavController().popBackStack(R.id.searchOnNameFragment, false)
            }
            b.doYouWantToPlay.text = getString(
                    R.string.do_you_want_to_play_with_the_player_P1,
                    viewModel!!.nameOfAnotherPlayer
            )
        }
    }

    override fun handleData(data: Data) {
        if (
                data is PlayTheGame ||
                data is PlayerWhoInvitedYouIsWaitingForAcceptingTheInvitationFromAnotherPlayer ||
                data is IncorrectAcceptingTheInvitation || data is IncorrectRefusalTheInvitation ||
                data is HardRemovalOfThePlayer
        ) {
            viewModel.notifyThatResponseToAcceptingTheInvitationWasReceived()
        }
        when (data) {
            is PlayTheGame -> {
                findNavController().navigate(R.id.gameFragment)
            }
            is PlayerWhoInvitedYouIsWaitingForAcceptingTheInvitationFromAnotherPlayer -> {
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