package com.example.final_project.presentation.screen.home.viewmodel

import androidx.lifecycle.ViewModel
import com.example.final_project.presentation.model.Offer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    val offers = listOf(
        Offer(1, "https://cdn.pixabay.com/photo/2016/02/23/17/42/orange-1218158_640.png", "Orange for only today"),
        Offer(2, "https://cdn.pixabay.com/photo/2016/02/24/17/31/fruit-1220367_640.png", "Grapefruit for only today"),
        Offer(3, "https://cdn.pixabay.com/photo/2016/03/03/17/15/fruit-1234656_1280.png", "Apple for only today"),
        Offer(4, "https://pixabay.com/illustrations/fruit-orange-png-transparent-1218158/", "Orange for only today"),
        Offer(5, "https://pixabay.com/illustrations/fruit-orange-png-transparent-1218158/", "Orange for only today"),
        Offer(6, "https://pixabay.com/illustrations/fruit-orange-png-transparent-1218158/", "Orange for only today"),
    )
    private val _offerStateFlow = MutableStateFlow(offers)
    val offerStateFlow = _offerStateFlow.asStateFlow()


}