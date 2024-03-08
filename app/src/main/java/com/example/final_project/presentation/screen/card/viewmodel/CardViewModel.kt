package com.example.final_project.presentation.screen.card.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.domain.usecase.card.GetCardsUseCase
import com.example.final_project.presentation.event.CardNavigationEvents
import com.example.final_project.presentation.event.ProfileNavigationUiEvents
import com.example.final_project.presentation.extension.decryptCardNumbers
import com.example.final_project.presentation.mapper.card.toPresentation
import com.example.final_project.presentation.model.card.Card
import com.example.final_project.presentation.state.CardsState
import com.example.final_project.presentation.state.SignOutState
import com.example.final_project.presentation.util.EncryptionHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(private val getCardsUseCase: GetCardsUseCase, private val encryptionHelper: EncryptionHelper) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<CardNavigationEvents>()
    val uiEvent: SharedFlow<CardNavigationEvents> get() = _uiEvent

    private val _cardsStateFlow = MutableStateFlow(CardsState())
    val cardsStateFlow: StateFlow<CardsState> get() = _cardsStateFlow

    val list = listOf(
        Card(
            id = 4769,
            cardNumber = "5105 1051 0510 5100",
            expirationDate = "06/28",
            cvv = "233",
            cardName = "Saxelfaso"
        ),

        Card(
            id = 476,
            cardNumber = "4917 3456 7890 1234",
            expirationDate = "06/28",
            cvv = "233",
            cardName = "Sakredito"
        )
    )

    fun onUiEvent(events: CardNavigationEvents) {
        when (events) {
            is CardNavigationEvents.NavigateBack -> {
                viewModelScope.launch {
                    _uiEvent.emit(CardNavigationEvents.NavigateBack)
                }
            }

            is CardNavigationEvents.NavigateToAddNewCard -> {
                viewModelScope.launch {
                    _uiEvent.emit(CardNavigationEvents.NavigateToAddNewCard)
                }
            }
        }
    }

    fun onEvent(event : CardsEvents) {
        when (event) {
            is CardsEvents.GetCardsFromDb -> getCards()
        }
    }

    private fun getCards() {
        viewModelScope.launch {
            getCardsUseCase().collect {
                val list = it.decryptCardNumbers { cardNumber -> encryptionHelper.decrypt(cardNumber) }

                _cardsStateFlow.update { currentState ->
                    currentState.copy(cards = list.map {
                        it.toPresentation()
                    })
                }
            }
        }
    }

    sealed interface CardsEvents {
        object GetCardsFromDb : CardsEvents
    }
}