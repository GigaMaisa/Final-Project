package com.example.final_project.domain.usecase.card

import com.example.final_project.domain.model.card.GetCard
import com.example.final_project.domain.repository.card.CardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(private val cardRepository: CardRepository) {
    operator fun invoke() : Flow<List<GetCard>> {
        return cardRepository.getUserCards()
    }
}