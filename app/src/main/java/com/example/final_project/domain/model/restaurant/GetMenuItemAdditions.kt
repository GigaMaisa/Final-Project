package com.example.final_project.domain.model.restaurant

data class GetMenuItemAdditions(
    val category: String,
    val options: List<GetMenuItemAdditionsOptions>
)
