package com.example.final_project.domain.usecase.chat

import com.example.final_project.domain.model.GetMessage
import com.example.final_project.domain.repository.ChatRepository
import javax.inject.Inject

class AddMessageUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(message: GetMessage, receiverUuid: String) {
        chatRepository.addMessage(message, receiverUuid)
    }
}