package com.example.final_project.data.repository.remote.chatbot

import com.example.final_project.data.remote.common.Resource
import com.example.final_project.data.remote.common.ResponseHandler
import com.example.final_project.data.remote.mapper.base.asResource
import com.example.final_project.data.remote.mapper.chatbot.toData
import com.example.final_project.data.remote.mapper.chatbot.toDomain
import com.example.final_project.data.remote.service.ChatBotApiService
import com.example.final_project.domain.model.bot.ChatBotResponse
import com.example.final_project.domain.model.bot.PostChatBotModel
import com.example.final_project.domain.repository.bot.ChatBotRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class ChatBotRepositoryImpl @Inject constructor(
    private val handler: ResponseHandler,
    private val chatBotApiService: ChatBotApiService
): ChatBotRepository {
    override suspend fun sendRequest(request: PostChatBotModel): Flow<Resource<ChatBotResponse>> {
        return handler.safeApiCall {
            chatBotApiService.postRequest(sessionId = UUID.randomUUID().toString(), body = request.toData())
        }.asResource {
            it.toDomain()
        }
    }
}