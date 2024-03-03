package com.example.final_project.presentation.screen.chat.chat_messaging.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.databinding.FragmentChatBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.chat.ChatEvent
import com.example.final_project.presentation.model.chat.Message
import com.example.final_project.presentation.screen.chat.adapter.MessageRecyclerViewAdapter
import com.example.final_project.presentation.screen.chat.chat_messaging.viewmodel.ChatViewModel
import com.example.final_project.presentation.state.ChatState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {

    private val viewModel: ChatViewModel by viewModels()
    private val safeArgs: ChatFragmentArgs by navArgs()
    private lateinit var receiverId: String
    private val messageAdapter = MessageRecyclerViewAdapter()

    override fun setUp() {
        receiverId = safeArgs.uuid
        viewModel.getReceiverId(receiverId)

        with(binding.charRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = messageAdapter
        }

        viewModel.onEvent(ChatEvent.GetMessagesEvent(receiverId))
    }

    override fun setUpListeners() {
        binding.sentButton.setOnClickListener {
            val message = Message(Random.nextLong(1, Long.MAX_VALUE), binding.messageBox.text.toString(), FirebaseAuth.getInstance().currentUser?.uid)
            viewModel.onEvent(ChatEvent.AddMessageEvent(message, receiverId))
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.chatStateFlow.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(state: ChatState) {
        with(state) {
            messages?.let {
                messageAdapter.submitList(it)
                binding.charRecyclerView.scrollToPosition(-1)
            }
        }
    }
}