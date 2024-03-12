package com.example.final_project.domain.usecase.chatbot

import com.example.final_project.data.local.datasource.ChatBotAuthTokenDataSource
import javax.inject.Inject

class UpdateAuthTokenUseCase @Inject constructor(private val chatBotAuthTokenDataSource: ChatBotAuthTokenDataSource) {
    suspend operator fun invoke() = chatBotAuthTokenDataSource.updateToken()
}