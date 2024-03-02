package com.example.final_project.domain.repository.chat

import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.model.chat.GetContact
import kotlinx.coroutines.flow.Flow

interface ChatContactsRepository {
    suspend fun getContacts(): Flow<Resource<List<GetContact>>>

    suspend fun addContact(contact:GetContact)
}