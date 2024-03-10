package com.example.final_project.presentation.screen.chatbot.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.usecase.chatbot.SendTextToChatBotUseCase
import com.example.final_project.presentation.event.bot.ChatBotEvents
import com.example.final_project.presentation.mapper.bot.toDomain
import com.example.final_project.presentation.mapper.bot.toPresentation
import com.example.final_project.presentation.mapper.chat.toPresentation
import com.example.final_project.presentation.model.chatbot.BotRequest
import com.example.final_project.presentation.state.ChatBotMessageState
import com.example.final_project.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatBotViewModel @Inject constructor(private val chatBotUseCase: SendTextToChatBotUseCase) : ViewModel() {
    private val _chatStateFlow = MutableStateFlow(ChatBotMessageState())
    val chatStateFlow = _chatStateFlow.asStateFlow()

    fun onEvent(event: ChatBotEvents) {
        when (event) {
            is ChatBotEvents.SendTextToChatBotEvent -> sendRequestToChatBot(event.text)
        }
    }

    private fun sendRequestToChatBot(text: String) {
        viewModelScope.launch {
            chatBotUseCase(BotRequest(text).toDomain()).collect { resource ->
                when(resource) {
                    is Resource.Loading -> _chatStateFlow.update { currentState -> currentState.copy(isLoading = resource.loading) }
                    is Resource.Error -> _chatStateFlow.update { currentState -> currentState.copy(errorMessage = getErrorMessage(resource.error)) }
                    is Resource.Success -> _chatStateFlow.update { currentState -> currentState.copy(messages = resource.response.toPresentation())}
                }
            }
        }
    }
}