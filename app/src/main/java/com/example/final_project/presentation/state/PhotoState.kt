package com.example.final_project.presentation.state

import android.net.Uri

data class PhotoState(
    val isLoading: Boolean = false,
    val imageUri: String? = null,
    val errorMessage: Int? = null
) {
}