package com.maksimzotov.quiz.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.maksimzotov.quiz.viewmodels.InvitationToPlayViewModel
import com.maksimzotov.quiz.R

class InvitationToPlayFragment : Fragment() {
    private val viewModel: InvitationToPlayViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_invitation_to_play, container, false)

        val buttonPlayWithAnotherPlayer: Button = view.findViewById(R.id.playWithAnotherPlayer) ?: throw Exception("Incorrect ID")
        buttonPlayWithAnotherPlayer.setOnClickListener { viewModel.acceptTheInvitation() }

        val buttonDoNotPlayWithAnotherPlayer: Button = view.findViewById(R.id.doNotPlayWithAnotherPlayer) ?: throw Exception("Incorrect ID")
        buttonDoNotPlayWithAnotherPlayer.setOnClickListener { viewModel.refuseTheInvitation() }

        return view
    }
}