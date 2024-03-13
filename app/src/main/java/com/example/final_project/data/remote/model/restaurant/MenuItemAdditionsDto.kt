package com.example.final_project.data.remote.model.restaurant

import com.squareup.moshi.Json

data class MenuItemAdditionsDto(
    val category: String,
    @Json(name = "data") val options: MenuItemOptionsDto
)
