package com.maksimzotov.quiz.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.maksimzotov.quiz.R
import com.maksimzotov.quiz.viewmodels.fragments.SearchOnNameViewModel

class SearchOnNameFragment : Fragment() {
    private val viewModel: SearchOnNameViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search_on_name, container, false)

        val toastShort = Observer<String> { message ->  Toast.makeText(activity, message, Toast.LENGTH_SHORT).show() }
        viewModel.toastShort.observe(viewLifecycleOwner, toastShort)

        val goToFragment = Observer<Int> { id ->  findNavController().navigate(id) }
        viewModel.goToFragment.observe(viewLifecycleOwner, goToFragment)

        val nameOfAnotherPlayer: EditText = view.findViewById(R.id.nameOfAnotherPlayer) ?: throw Exception("Incorrect ID")
        nameOfAnotherPlayer.doAfterTextChanged { viewModel.nameOfAnotherPlayer = nameOfAnotherPlayer.text.toString() }

        val buttonNext: Button = view.findViewById(R.id.inviteAnotherPlayer) ?: throw Exception("Incorrect ID")
        buttonNext.setOnClickListener { viewModel.inviteAnotherPlayer() }

        return view
    }
}