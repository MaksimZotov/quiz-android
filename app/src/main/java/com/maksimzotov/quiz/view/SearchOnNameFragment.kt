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
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.maksimzotov.quiz.R
import com.maksimzotov.quiz.model.communication.ReceiverFromServer
import com.maksimzotov.quiz.viewmodels.SearchOnNameViewModel
import data.*

class SearchOnNameFragment : Fragment() {
    private val viewModel: SearchOnNameViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search_on_name, container, false)

        val nameOfAnotherPlayer: EditText = view.findViewById(R.id.nameOfAnotherPlayer) ?: throw Exception("Incorrect ID")
        nameOfAnotherPlayer.doAfterTextChanged { viewModel.nameOfAnotherPlayer = nameOfAnotherPlayer.text.toString() }

        val buttonNext: Button = view.findViewById(R.id.inviteAnotherPlayer) ?: throw Exception("Incorrect ID")
        buttonNext.setOnClickListener { viewModel.inviteAnotherPlayer() }

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
                is Invitation -> {
                    viewModel.handleInvitation(data.name)
                    findNavController().navigate(R.id.invitationToPlayFragment)
                }
                is PlayTheGame -> {
                    findNavController().navigate(R.id.gameFragment)
                }
                is RefusalTheInvitation -> {
                    Toast.makeText(activity, "The player ${data.name} has refused the invitation", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    throw Exception("Incorrect data for the SearchOnName fragment")
                }
            }
        }
        viewModel.data.observe(viewLifecycleOwner, dataObserver)
    }
}