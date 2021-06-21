package com.maksimzotov.quiz.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.maksimzotov.quiz.R
import com.maksimzotov.quiz.databinding.FragmentInvitationToPlayBinding
import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.model.communication.ReceiverFromServer
import com.maksimzotov.quiz.viewmodel.InvitationToPlayViewModel
import data.*

class InvitationToPlayFragment : Fragment() {
    private val viewModel: InvitationToPlayViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        observeDataFromServer()
        val binding: FragmentInvitationToPlayBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_invitation_to_play, container, false
        )
        binding.viewModel = viewModel

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.refuseTheInvitation()
                findNavController().popBackStack()
            }
        })

        return binding.root
    }

    private fun observeDataFromServer() {
        ReceiverFromServer.setObserver(viewModel)
        val dataObserver = Observer<Data> { data ->
            when (data) {
                is PlayTheGame -> {
                    val navController = findNavController()
                    navController.popBackStack()
                    navController.navigate(R.id.gameFragment)
                }
                is ThePlayerWhoInvitedYouIsWaitingForAcceptingTheInvitationFromAnotherPlayer -> {
                    Toast.makeText(activity, "The player \"${data.name}\" is waiting for accepting the invitation from another player", Toast.LENGTH_SHORT).show()
                }
                is IncorrectAcceptingTheInvitation -> {
                    Toast.makeText(activity, "The player \"${data.name}\" does not exist", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                is IncorrectRefusalTheInvitation -> {
                    // do nothing
                }
                is HardRemovalOfThePlayer -> {
                    Toast.makeText(activity, "Unknown error on the side of another player", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                else -> {
                    throw Exception("Incorrect data for the SearchOnName fragment")
                }
            }
        }
        viewModel.data.observe(viewLifecycleOwner, dataObserver)
    }
}