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
import com.maksimzotov.quiz.model.communication.ReceiverFromServer
import com.maksimzotov.quiz.viewmodels.AuthenticationViewModel
import data.AcceptingTheName
import data.Data
import data.RefusalTheName

class AuthenticationFragment : Fragment() {
    private val viewModel: AuthenticationViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_authentication, container, false)

        val playerName: EditText = view.findViewById(R.id.userName) ?: throw Exception("Incorrect ID")
        playerName.doAfterTextChanged { viewModel.playerName = playerName.text.toString() }

        val buttonNext: Button = view.findViewById(R.id.goToChooseNameOfAnotherPlayer) ?: throw Exception("Incorrect ID")
        buttonNext.setOnClickListener { viewModel.sendPlayerName() }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeDataFromServer()
    }

    private fun observeDataFromServer() {
        ReceiverFromServer.setObserver(viewModel)
        val dataObserver = Observer<Data> { data ->
            when (data) {
                is RefusalTheName -> {
                    Toast.makeText(activity, "The name ${data.name} is taken", Toast.LENGTH_SHORT).show()
                }
                is AcceptingTheName -> {
                    findNavController().navigate(R.id.searchOnNameFragment)
                }
                else -> {
                    throw Exception("Incorrect data for the Authentication fragment")
                }
            }
        }
        viewModel.data.observe(viewLifecycleOwner, dataObserver)
    }
}