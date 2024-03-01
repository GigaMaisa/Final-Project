package com.example.final_project.presentation.state

import com.example.final_project.presentation.model.Message

data class ChatState(
    val messages: MutableList<Message>? = null,
    val receiverId: String? = null
)
