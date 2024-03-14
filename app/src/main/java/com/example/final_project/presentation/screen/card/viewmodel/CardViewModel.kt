package com.example.final_project.presentation.screen.card.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.domain.usecase.card.GetCardsUseCase
import com.example.final_project.presentation.event.CardNavigationEvents
import com.example.final_project.presentation.extension.decryptCardNumbers
import com.example.final_project.presentation.mapper.card.toPresentation
import com.example.final_project.presentation.state.CardsState
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