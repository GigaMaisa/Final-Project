package com.example.final_project.data.remote.model.restaurant

import com.squareup.moshi.Json

data class MenuItemDetailsDto(
    val description: String = "",
    val id: String,
    val image: String,
    val name: String,
    val price: Double,
    @Json(name = "additions") val menuItemAdditions: List<MenuItemAdditionsDto>
)
