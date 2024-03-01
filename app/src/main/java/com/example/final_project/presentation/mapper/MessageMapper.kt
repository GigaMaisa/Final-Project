package com.example.final_project.presentation.mapper

import com.example.final_project.domain.model.GetMessage
import com.example.final_project.presentation.model.Message

fun Message.toDomain() = GetMessage(
    id = id,
    message = message,
    senderId = senderId
)

fun GetMessage.toPresentation() = Message(
    id = id,
    message = message,
    senderId = senderId
)