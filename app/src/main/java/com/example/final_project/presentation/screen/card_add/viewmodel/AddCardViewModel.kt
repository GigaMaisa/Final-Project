package com.example.final_project.presentation.screen.card_add.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.domain.model.card.GetCard
import com.example.final_project.domain.usecase.card.AddCardUseCase
import com.example.final_project.presentation.event.AddCardNavigationEvents
import com.example.final_project.presentation.util.EncryptionHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCardViewModel @Inject constructor(private val addCardUseCase: AddCardUseCase, private val encryptionHelper: EncryptionHelper) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<AddCardNavigationEvents>()
    val uiEvent: SharedFlow<AddCardNavigationEvents> get() = _uiEvent

    fun onEvent(events: AddCardEvents) {
        when (events) {
            is AddCardEvents.AddCard -> addCard(
                cardNumber = events.cardNumber,
                expiryDate = events.expiryDate,
                cvv = events.cvv,
                cardName = events.cardName
            )
        }
    }

    private fun addCard(cardNumber: String, expiryDate: String, cvv: String, cardName: String) {
        viewModelScope.launch {
            addCardUseCase(
                GetCard(id = 0,
                    cardNumber = encryptionHelper.encrypt(cardNumber),
                    expirationDate = encryptionHelper.encrypt(expiryDate),
                    cvv = encryptionHelper.encrypt(cvv),
                    cardName = cardName)
            )

            _uiEvent.emit(AddCardNavigationEvents.NavigateBack)
        }
    }
}

sealed class AddCardEvents {
    data class AddCard(val cardNumber: String, val expiryDate: String, val cvv: String, val cardName: String) : AddCardEvents()
}