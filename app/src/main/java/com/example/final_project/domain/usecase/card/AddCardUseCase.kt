package com.example.final_project.domain.usecase.card

import com.example.final_project.domain.model.card.GetCard
import com.example.final_project.domain.repository.card.CardRepository
import javax.inject.Inject

class AddCardUseCase @Inject constructor(private val cardRepository: CardRepository) {
    suspend operator fun invoke(card: GetCard) {
        cardRepository.insertCard(card)
    }
}