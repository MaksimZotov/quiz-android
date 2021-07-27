package com.maksimzotov.quiz.view

import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import androidx.fragment.app.viewModels
import com.maksimzotov.quiz.R
import androidx.navigation.fragment.findNavController
import com.maksimzotov.quiz.databinding.FragmentAuthenticationBinding
import com.maksimzotov.quiz.util.shortToast
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
        binding.also { b ->
            b.viewModel = viewModel
            b.goToChooseNameOfAnotherPlayer.setOnClickListener {
                val cm = requireActivity()
                        .getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
                val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
                val connected = capabilities?.hasCapability(NET_CAPABILITY_INTERNET) == true
                if (connected) {
                    viewModel.sendPlayerName()
                }
            }
        }
    }

    override fun handleData(data: Data) {
        when (data) {
            is RefusalTheName -> {
                viewModel.notifyThatResponseToNameWasReceived()
                shortToast(activity, getString(R.string.the_name_P1_is_taken, data.name))
            }
            is AcceptingTheName -> {
                viewModel.notifyThatResponseToNameWasReceived()
                findNavController().navigate(R.id.searchOnNameFragment)
            }
        }
    }
}