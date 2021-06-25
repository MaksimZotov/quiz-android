package com.maksimzotov.quiz.view

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.maksimzotov.quiz.R
import com.maksimzotov.quiz.databinding.FragmentFinishGameBinding
import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.util.shortToast
import com.maksimzotov.quiz.view.base.BaseFragment
import com.maksimzotov.quiz.viewmodel.FinishGameViewModel
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
            finishText.text = getString(
                    R.string.finish_your_score_P1_score_of_another_player_P2_P3,
                    viewModel!!.playerScore,
                    viewModel!!.nameOfAnotherPlayer,
                    viewModel!!.scoreOfAnotherPlayer
            )
        }
    }

    override fun handleData(data: Data) {
        when (data) {
            is PlayTheGame -> {
                viewModel.notifyThatResponseToRequestToPlayAgainWasReceived()
                findNavController().popBackStack(R.id.gameFragment, false)
            }
            is RefusalToPlayAgain -> {
                viewModel.notifyThatResponseToRequestToPlayAgainWasReceived()
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
        }
    }

    override fun onBackPressed() {
        viewModel.chooseAnotherPlayer()
        findNavController().popBackStack(R.id.searchOnNameFragment, false)
    }
}