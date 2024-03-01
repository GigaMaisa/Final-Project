package com.example.final_project.presentation.screen.chat.chat_messaging.viewmodel

import androidx.lifecycle.ViewModel
import com.example.final_project.presentation.model.Message
import com.example.final_project.presentation.state.ChatState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChatViewModel: ViewModel() {
    private val _chatStateFlow = MutableStateFlow(ChatState())
    val chatStateFlow = _chatStateFlow.asStateFlow()

    fun fetchInitialChat(messages: List<Message>) {
        _chatStateFlow.update { currentState -> currentState.copy(messages = messages.toMutableList()) }
    }

    fun getReceiverId(id: String) {
        _chatStateFlow.update { currentState -> currentState.copy(receiverId = id) }
    }

    fun addMessage(message: Message) {
        val updatedList = _chatStateFlow.value.messages
        updatedList?.add(message)
        _chatStateFlow.update { currentState -> currentState.copy(messages = updatedList) }
    }
}