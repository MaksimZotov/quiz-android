package com.maksimzotov.quiz.view

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.maksimzotov.quiz.R
import com.maksimzotov.quiz.databinding.FragmentGameBinding
import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.util.shortToast
import com.maksimzotov.quiz.view.base.BaseFragment
import com.maksimzotov.quiz.viewmodel.GameViewModel
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
                shortToast(
                        activity,
                        getString(
                                R.string.the_player_P1_has_left_the_game,
                                AppState.nameOfAnotherPlayer
                        )
                )
                findNavController().popBackStack(R.id.searchOnNameFragment, false)
            }
            is FinishTheGame -> {
                findNavController().navigate(R.id.finishGameFragment)
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
        viewModel.leaveGame()
        findNavController().popBackStack(R.id.searchOnNameFragment, false)
    }
}