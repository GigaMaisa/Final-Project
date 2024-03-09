package com.example.final_project.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_restaurants")
data class RestaurantEntity(
    @PrimaryKey val restaurantId: Int,
    val title: String,
    val type: String,
    val deliveryFee: Int,
    val deliveryTime: String,
    val image: String,
    val rating: Double
)
