package com.maksimzotov.quiz.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.maksimzotov.quiz.R
import com.maksimzotov.quiz.databinding.FragmentAuthenticationBinding
import com.maksimzotov.quiz.model.communication.ReceiverFromServer
import com.maksimzotov.quiz.viewmodels.InvitationToPlayViewModel
import data.Data
import data.PlayTheGame

class InvitationToPlayFragment : Fragment() {
    private val viewModel: InvitationToPlayViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        observeDataFromServer()
        val binding: FragmentAuthenticationBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search_on_name, container, false
        )
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun observeDataFromServer() {
        ReceiverFromServer.setObserver(viewModel)
        val dataObserver = Observer<Data> { data ->
            when (data) {
                is PlayTheGame -> {
                    findNavController().navigate(R.id.gameFragment)
                }
                else -> {
                    throw Exception("Incorrect data for the SearchOnName fragment")
                }
            }
        }
        viewModel.data.observe(viewLifecycleOwner, dataObserver)
    }
}