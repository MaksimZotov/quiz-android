package com.maksimzotov.quiz.view.base

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.maksimzotov.quiz.R
import com.maksimzotov.quiz.model.communication.ReceiverFromServer
import com.maksimzotov.quiz.model.communication.SenderToServer
import com.maksimzotov.quiz.util.ConnectionLostCallback
import com.maksimzotov.quiz.viewmodel.base.BaseViewModel
import data.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class BaseFragment<VB: ViewBinding, VM: BaseViewModel>(
        private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {
    abstract val viewModel: VM
    abstract fun handleData(data: Data)
    abstract fun assignBinding(binding: VB)

    open fun onBackPressed() {
        findNavController().popBackStack()
    }


    private var _binding: VB? = null
    val binding get() = _binding!!

    private val handleDataObserver = Observer<Data> { data -> handleData(data) }

    private val connectivityCallback = object : ConnectionLostCallback() {
        override fun notifyThatConnectionLost() {
            GlobalScope.launch(Dispatchers.IO) {
                SenderToServer.closeConnection()
            }
            GlobalScope.launch(Dispatchers.Main) {
                findNavController().popBackStack(R.id.authenticationFragment, false)
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val cm = requireActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerDefaultNetworkCallback(connectivityCallback)

        _binding = inflate.invoke(inflater, container, false)
        observeDataFromServer()
        assignBinding(binding)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        val cm = requireActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.unregisterNetworkCallback(connectivityCallback)

        _binding = null
    }

    private fun observeDataFromServer() {
        ReceiverFromServer.setObserver(viewModel)
        viewModel.data.observe(viewLifecycleOwner, handleDataObserver)
    }
}