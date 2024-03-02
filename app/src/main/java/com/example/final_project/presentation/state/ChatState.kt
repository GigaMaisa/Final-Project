package com.example.final_project.presentation.state

import com.example.final_project.presentation.model.chat.Message

data class ChatState(
    val messages: MutableList<Message>? = null,
    val receiverId: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: Int? = null
)
