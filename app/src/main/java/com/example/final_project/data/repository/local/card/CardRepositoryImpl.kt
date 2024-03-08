package com.example.final_project.data.repository.local.card

import com.example.final_project.data.local.dao.CardDao
import com.example.final_project.data.local.mapper.cards.toData
import com.example.final_project.data.local.mapper.cards.toDomain
import com.example.final_project.domain.model.card.GetCard
import com.example.final_project.domain.repository.card.CardRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(private val cardDao: CardDao, private val ioDispatcher: CoroutineDispatcher): CardRepository {
    override fun getUserCards(): Flow<List<GetCard>> {
        return cardDao.getAllCards().map { it.map { it.toDomain() } }.flowOn(ioDispatcher)
    }

    override suspend fun insertCard(card: GetCard) = withContext(ioDispatcher) {
        cardDao.insertCard(card.toData())
    }
}