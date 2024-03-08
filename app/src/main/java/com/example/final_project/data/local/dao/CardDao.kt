package com.example.final_project.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.final_project.data.local.model.CardEntity
import com.example.final_project.data.remote.common.Resource
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Query("SELECT * FROM user_cards")
    fun getAllCards(): Flow<List<CardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: CardEntity)
}