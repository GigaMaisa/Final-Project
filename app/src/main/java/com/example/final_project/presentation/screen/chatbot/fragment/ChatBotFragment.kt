package com.example.final_project.presentation.screen.chatbot.fragment

import androidx.fragment.app.viewModels
import com.example.final_project.databinding.FragmentChatBotBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.bot.ChatBotEvents
import com.example.final_project.presentation.screen.chatbot.viewmodel.ChatBotViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatBotFragment : BaseFragment<FragmentChatBotBinding>(FragmentChatBotBinding::inflate) {
    private val viewModel: ChatBotViewModel by viewModels()

    override fun setUp() {
        viewModel.onEvent(ChatBotEvents.SendTextToChatBotEvent("Hello Dear Friend"))
    }

    override fun setUpListeners() {

    }

    override fun setUpObservers() {

    }
}