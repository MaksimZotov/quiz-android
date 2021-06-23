package com.maksimzotov.quiz.view

import android.content.Context
import android.net.ConnectivityManager
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
import com.maksimzotov.quiz.databinding.FragmentSearchOnNameBinding
import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.model.communication.ReceiverFromServer
import com.maksimzotov.quiz.model.communication.SenderToServer
import com.maksimzotov.quiz.util.ConnectionLostCallback
import com.maksimzotov.quiz.viewmodel.SearchOnNameViewModel
import data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchOnNameFragment : Fragment() {
    private val viewModel: SearchOnNameViewModel by viewModels()

    private val connectivityCallback = object : ConnectionLostCallback() {
        override fun notifyThatConnectionLost() {
            GlobalScope.launch(Dispatchers.IO) {
                SenderToServer.closeConnection()
            }
            GlobalScope.launch(Dispatchers.Main) {
                findNavController().popBackStack()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val cm = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerDefaultNetworkCallback(connectivityCallback)
    }

    override fun onDetach() {
        super.onDetach()
        val cm = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.unregisterNetworkCallback(connectivityCallback)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        observeDataFromServer()
        val binding: FragmentSearchOnNameBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_search_on_name, container, false
        )
        binding.viewModel = viewModel

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.changeName()
                findNavController().popBackStack()
            }
        })

        return binding.root
    }

    private fun observeDataFromServer() {
        ReceiverFromServer.setObserver(viewModel)
        val dataObserver = Observer<Data> { data ->
            when (data) {
                is Invitation -> {
                    AppState.waitingForPlayTheGame = false
                    viewModel.handleInvitation(data.name)
                    findNavController().navigate(R.id.invitationToPlayFragment)
                }
                is PlayTheGame -> {
                    AppState.waitingForPlayTheGame = false
                    findNavController().navigate(R.id.gameFragment)
                }
                is RefusalTheInvitation -> {
                    AppState.waitingForPlayTheGame = false
                    Toast.makeText(activity, "The player \"${data.name}\" has refused the invitation", Toast.LENGTH_SHORT).show()
                }
                is IncorrectInvitation -> {
                    AppState.waitingForPlayTheGame = false
                    Toast.makeText(activity, "The player \"${data.name}\" does not exist", Toast.LENGTH_SHORT).show()
                }
                is InvitationMyself -> {
                    AppState.waitingForPlayTheGame = false
                    Toast.makeText(activity, "You can't invite yourself", Toast.LENGTH_SHORT).show()
                }
                is InvitedPlayerIsDecidingWhetherToPlayWithAnotherPlayer -> {
                    AppState.waitingForPlayTheGame = false
                    Toast.makeText(activity, "The invited player \"${data.name}\" is deciding whether to play with another player", Toast.LENGTH_SHORT).show()
                }
                is HardRemovalOfThePlayer -> {
                    AppState.waitingForPlayTheGame = false
                    Toast.makeText(activity, "Unknown error on the side of another player", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    throw Exception("Incorrect data for the SearchOnName fragment")
                }
            }
        }
        viewModel.data.observe(viewLifecycleOwner, dataObserver)
    }
}