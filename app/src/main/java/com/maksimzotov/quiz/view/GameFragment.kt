package com.maksimzotov.quiz.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.widget.Button
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

            viewModel!!.onStart()

            giveAnswer.setBackgroundColor(resources.getColor(R.color.blue))
            giveAnswer.setOnClickListener {
                if (viewModel!!.isAbleToGiveAnswer) {
                    if (viewModel!!.giveAnswer()) {
                        giveAnswer.setBackgroundColor(resources.getColor(R.color.green))
                    } else {
                        giveAnswer.setBackgroundColor(resources.getColor(R.color.red))
                    }
                }
            }


            val listOfAnswerButtons = listOf(answer0, answer1, answer2)
            listOfAnswerButtons.forEach { it.setBackgroundColor(resources.getColor(R.color.blue)) }
            val buttonAnswerFunction: (button: Button, index: Int) -> Unit = { button, index ->
                if (viewModel!!.isAbleToGiveAnswer) {
                    viewModel!!.setAnswer(index)
                    button.setBackgroundColor(resources.getColor(R.color.yellow))
                    listOfAnswerButtons.forEach {
                        if (it != button) {
                            it.setBackgroundColor(resources.getColor(R.color.blue))
                        }
                    }
                }
            }
            listOfAnswerButtons.forEachIndexed { index, button ->
                button.setOnClickListener { buttonAnswerFunction(button, index) }
            }
        }
    }

    override fun handleData(data: Data) {
        when (data) {
            is Question -> {
                with(binding) {
                    listOf(giveAnswer, answer0, answer1, answer2).forEach {
                        it.setBackgroundColor(resources.getColor(R.color.blue))
                    }
                }
            }
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
                viewModel.saveGameState()
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