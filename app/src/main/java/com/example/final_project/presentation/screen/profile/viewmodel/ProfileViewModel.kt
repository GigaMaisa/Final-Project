package com.example.final_project.presentation.screen.profile.viewmodel

import androidx.lifecycle.ViewModel
import com.example.final_project.presentation.model.cart.CartItem
import com.example.final_project.presentation.state.ProfileState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel: ViewModel() {
    val cartItems = listOf(
        CartItem(1, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 50.0, 2, 3.5),
        CartItem(2, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7, 1, 3.5),
        CartItem(3, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7, 4, 3.5),
        CartItem(4, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7, 5, 3.5),
        CartItem(5, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7, 1, 3.5),
        CartItem(6, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7, 6, 3.5),
        CartItem(7, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7, 1, 3.5),
        CartItem(8, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7, 2, 3.5),
    )

    private val _profileStateFlow = MutableStateFlow(ProfileState())
    val profileStateFlow = _profileStateFlow.asStateFlow()

    init {
        _profileStateFlow.update { currentState -> currentState.copy(favourites = cartItems) }
    }
}