package com.example.final_project.domain.repository

import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.model.GetMessage
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getMessages(receiverUuid: String): Flow<Resource<List<GetMessage>>>

    suspend fun addMessage(message: GetMessage)
}