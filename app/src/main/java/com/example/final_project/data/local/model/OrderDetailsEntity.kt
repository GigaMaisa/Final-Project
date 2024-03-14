package com.example.final_project.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_details")
data class OrderDetailsEntity(
    @PrimaryKey val foodId: String,
    val restaurantId: Int,
    val name: String,
    val image: String,
    val menuCategory: String,
    val quantity: Int,
    val price: Double
)
