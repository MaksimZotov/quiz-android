package com.maksimzotov.quiz.view

import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.maksimzotov.quiz.R
import androidx.navigation.fragment.findNavController
import com.maksimzotov.quiz.databinding.FragmentAuthenticationBinding
import com.maksimzotov.quiz.model.appstate.AppState
import com.maksimzotov.quiz.view.base.BaseFragment
import com.maksimzotov.quiz.viewmodel.AuthenticationViewModel
import data.AcceptingTheName
import data.Data
import data.RefusalTheName

class AuthenticationFragment :
        BaseFragment
            <FragmentAuthenticationBinding, AuthenticationViewModel>
                (FragmentAuthenticationBinding::inflate) {

    override val viewModel: AuthenticationViewModel by viewModels()

    override fun assignBinding(binding: FragmentAuthenticationBinding) {
        with(binding) {
            viewModel = this@AuthenticationFragment.viewModel
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
    }

    override fun handleData(data: Data) {
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
}