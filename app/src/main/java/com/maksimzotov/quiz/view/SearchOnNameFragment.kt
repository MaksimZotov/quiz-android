package com.maksimzotov.quiz.view

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.maksimzotov.quiz.R
import com.maksimzotov.quiz.databinding.FragmentSearchOnNameBinding
import com.maksimzotov.quiz.util.shortToast
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
        if (
                data is Invitation || data is PlayTheGame || data is RefusalTheInvitation ||
                data is IncorrectInvitation || data is InvitationMyself ||
                data is InvitedPlayerIsDecidingWhetherToPlayWithAnotherPlayer ||
                data is HardRemovalOfThePlayer
        ) {
            viewModel.notifyThatResponseToInvitationWasReceived()
        }
        when (data) {
            is Invitation -> {
                viewModel.handleInvitation(data.name)
                findNavController().navigate(R.id.invitationToPlayFragment)
            }
            is PlayTheGame -> {
                findNavController().navigate(R.id.gameFragment)
            }
            is RefusalTheInvitation -> {
                shortToast(
                        activity,
                        getString(R.string.the_player_P1_has_refused_the_invitation, data.name)
                )
            }
            is IncorrectInvitation -> {
                shortToast(activity, getString(R.string.the_player_P1_does_not_exist, data.name))
            }
            is InvitationMyself -> {
                shortToast(activity, getString(R.string.you_can_not_invite_yourself))
            }
            is InvitedPlayerIsDecidingWhetherToPlayWithAnotherPlayer -> {
                shortToast(
                        activity,
                        getString(
                                R.string.invited_player_P1_is_deciding_whether_to_play_with_another,
                                data.name
                        )
                )
            }
            is HardRemovalOfThePlayer -> {
                shortToast(
                        activity,
                        getString(R.string.unknown_error_on_the_side_of_another_player)
                )
            }
        }
    }

    override fun onBackPressed() {
        viewModel.changeName()
        findNavController().popBackStack(R.id.authenticationFragment, false)
    }
}