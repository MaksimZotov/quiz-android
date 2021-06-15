package com.maksimzotov.quiz.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.maksimzotov.quiz.R
import com.maksimzotov.quiz.viewmodels.fragments.GameViewModel

class GameFragment : Fragment() {
    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        val toastShort = Observer<String> { message ->  Toast.makeText(activity, message, Toast.LENGTH_SHORT).show() }
        viewModel.toastShort.observe(viewLifecycleOwner, toastShort)

        val goToFragment = Observer<Int> { id ->  findNavController().navigate(id) }
        viewModel.goToFragment.observe(viewLifecycleOwner, goToFragment)


        val buttonAnswer0: Button = view.findViewById(R.id.answer0) ?: throw Exception("Incorrect ID")
        buttonAnswer0.setOnClickListener { viewModel.setAnswer(0) }

        val buttonAnswer1: Button = view.findViewById(R.id.answer0) ?: throw Exception("Incorrect ID")
        buttonAnswer1.setOnClickListener { viewModel.setAnswer(1) }

        val buttonAnswer2: Button = view.findViewById(R.id.answer0) ?: throw Exception("Incorrect ID")
        buttonAnswer2.setOnClickListener { viewModel.setAnswer(2) }

        val buttonGiveAnswer: Button = view.findViewById(R.id.giveAnswer) ?: throw Exception("Incorrect ID")
        buttonGiveAnswer.setOnClickListener { viewModel.giveAnswer() }


        val questionText: TextView = view.findViewById(R.id.question) ?: throw Exception("Incorrect ID")
        val question = Observer<String> { text ->  questionText.text = text }
        viewModel.question.observe(viewLifecycleOwner, question)

        val firstAnswer = Observer<String> { text ->  buttonAnswer0.text = text }
        viewModel.firstAnswer.observe(viewLifecycleOwner, firstAnswer)

        val secondAnswer = Observer<String> { text ->  buttonAnswer1.text = text }
        viewModel.secondAnswer.observe(viewLifecycleOwner, secondAnswer)

        val thirdAnswer = Observer<String> { text ->  buttonAnswer2.text = text }
        viewModel.thirdAnswer.observe(viewLifecycleOwner, thirdAnswer)


        val remainingTimeText: TextView = view.findViewById(R.id.remainingTime) ?: throw Exception("Incorrect ID")
        val remainingTime = Observer<Double> { time ->  remainingTimeText.text = "Time: ${time}" }
        viewModel.remainingTime.observe(viewLifecycleOwner, remainingTime)


        val score: TextView = view.findViewById(R.id.score) ?: throw Exception("Incorrect ID")
        val scorePair = Observer<Pair<Int, Int>> { pair -> score.text = "Score: ${pair.first}/${pair.second}" }
        viewModel.scorePair.observe(viewLifecycleOwner, scorePair)

        return view
    }
}