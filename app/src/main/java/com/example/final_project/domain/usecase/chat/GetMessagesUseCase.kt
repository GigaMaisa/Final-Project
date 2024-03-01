package com.example.final_project.domain.usecase.chat

import com.example.final_project.domain.repository.ChatRepository
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(receiverUuid: String) = chatRepository.getMessages(receiverUuid)
}