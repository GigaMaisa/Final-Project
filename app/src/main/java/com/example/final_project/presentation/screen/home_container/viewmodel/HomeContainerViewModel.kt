package com.example.final_project.presentation.screen.home_container.viewmodel

import androidx.lifecycle.ViewModel
import com.example.final_project.domain.usecase.delivery_location.GetDeliveryLocationUseCase
import com.example.final_project.presentation.state.CourierDeliveryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeContainerViewModel @Inject constructor(
    private val getDeliveryLocationUseCase: GetDeliveryLocationUseCase
) : ViewModel() {
    val defaultLocationFlow = getDeliveryLocationUseCase()
}