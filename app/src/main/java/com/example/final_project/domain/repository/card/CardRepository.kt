package com.example.final_project.domain.repository.card

import com.example.final_project.domain.model.card.GetCard
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    fun getUserCards() : Flow<List<GetCard>>

    suspend fun insertCard(card: GetCard)
}