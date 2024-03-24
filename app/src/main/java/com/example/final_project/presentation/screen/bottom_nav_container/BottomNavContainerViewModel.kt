package com.example.final_project.presentation.screen.bottom_nav_container

import androidx.lifecycle.ViewModel
import com.example.final_project.domain.usecase.restaurant.GetCartItemsNumberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BottomNavContainerViewModel @Inject constructor(getCartItemsNumberUseCase: GetCartItemsNumberUseCase): ViewModel() {
    val cartItemsNumber = getCartItemsNumberUseCase()
}