package com.maksimzotov.quiz.view

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
import com.maksimzotov.quiz.R
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.maksimzotov.quiz.viewmodels.AuthenticationViewModel

class AuthenticationFragment : Fragment() {
    private val viewModel: AuthenticationViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_authentication, container, false)

        val toastShort = Observer<String> { message ->  Toast.makeText(activity, message, Toast.LENGTH_SHORT).show() }
        viewModel.toastShort.observe(viewLifecycleOwner, toastShort)

        val goToFragment = Observer<Int> { id ->  findNavController().navigate(id) }
        viewModel.goToFragment.observe(viewLifecycleOwner, goToFragment)

        val playerName: EditText = view.findViewById(R.id.userName) ?: throw Exception("Incorrect ID")
        playerName.doAfterTextChanged { viewModel.playerName = playerName.text.toString() }

        val buttonNext: Button = view.findViewById(R.id.goToChooseNameOfAnotherPlayer) ?: throw Exception("Incorrect ID")
        buttonNext.setOnClickListener { viewModel.setPlayerName() }

        return view
    }
}