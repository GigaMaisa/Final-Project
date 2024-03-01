package com.example.final_project.data.remote.mapper

import com.example.final_project.data.remote.model.MessageDto
import com.example.final_project.domain.model.GetMessage

fun MessageDto.toDomain() = GetMessage(
    id = id,
    message = message,
    senderId = senderId
)