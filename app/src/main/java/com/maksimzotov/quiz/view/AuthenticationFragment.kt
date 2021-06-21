package com.maksimzotov.quiz.view

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
import com.maksimzotov.quiz.model.communication.ReceiverFromServer
import com.maksimzotov.quiz.viewmodel.AuthenticationViewModel
import data.AcceptingTheName
import data.Data
import data.RefusalTheName

class AuthenticationFragment : Fragment() {
    private val viewModel: AuthenticationViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        observeDataFromServer()
        val binding: FragmentAuthenticationBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_authentication, container, false
        )
        binding.viewModel = viewModel
        return binding.root
    }

    private fun observeDataFromServer() {
        ReceiverFromServer.setObserver(viewModel)
        val dataObserver = Observer<Data> { data ->
            when (data) {
                is RefusalTheName -> {
                    Toast.makeText(activity, "The name \"${data.name}\" is taken", Toast.LENGTH_SHORT).show()
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