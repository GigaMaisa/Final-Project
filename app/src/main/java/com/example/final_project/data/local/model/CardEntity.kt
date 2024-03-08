package com.example.final_project.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_cards")
data class CardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val cardNumber: String,
    val expirationDate: String,
    val cvv: String,
    val cardName: String
)