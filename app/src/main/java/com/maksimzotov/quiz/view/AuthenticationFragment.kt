package com.maksimzotov.quiz.view

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.maksimzotov.quiz.R
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.maksimzotov.quiz.databinding.FragmentAuthenticationBinding
import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.model.communication.ReceiverFromServer
import com.maksimzotov.quiz.model.communication.SenderToServer
import com.maksimzotov.quiz.util.ConnectionLostCallback
import com.maksimzotov.quiz.viewmodel.AuthenticationViewModel
import data.AcceptingTheName
import data.Data
import data.RefusalTheName

class AuthenticationFragment : Fragment() {
    private val viewModel: AuthenticationViewModel by viewModels()

    private val connectivityCallback = object : ConnectionLostCallback() {
        override fun notifyThatConnectionLost() {
            SenderToServer.closeConnection()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val cm = requireActivity().getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerDefaultNetworkCallback(connectivityCallback)
    }

    override fun onDetach() {
        super.onDetach()
        val cm = requireActivity().getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.unregisterNetworkCallback(connectivityCallback)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        observeDataFromServer()
        val binding: FragmentAuthenticationBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_authentication, container, false
        )
        binding.viewModel = viewModel

        with(binding) {
            goToChooseNameOfAnotherPlayer.setOnClickListener {
                val cm = requireActivity()
                    .getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
                val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
                val connected = capabilities?.hasCapability(NET_CAPABILITY_INTERNET) == true
                if (connected) {
                    viewModel!!.sendPlayerName()
                }
            }
        }

        return binding.root
    }

    private fun observeDataFromServer() {
        ReceiverFromServer.setObserver(viewModel)
        val dataObserver = Observer<Data> { data ->
            when (data) {
                is RefusalTheName -> {
                    AppState.waitingForAcceptingTheName = false
                    Toast.makeText(activity, "The name \"${data.name}\" is taken", Toast.LENGTH_SHORT).show()
                }
                is AcceptingTheName -> {
                    AppState.waitingForAcceptingTheName = false
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