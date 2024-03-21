package com.example.final_project.presentation.screen.card_add.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.R
import com.example.final_project.domain.model.card.GetCard
import com.example.final_project.domain.usecase.card.AddCardUseCase
import com.example.final_project.domain.usecase.validators.CardCvvValidationUseCase
import com.example.final_project.domain.usecase.validators.CardExpiryDateValidationUseCase
import com.example.final_project.domain.usecase.validators.CardNameValidationUseCase
import com.example.final_project.domain.usecase.validators.CardNumberValidationUseCase
import com.example.final_project.presentation.event.AddCardNavigationEvents
import com.example.final_project.presentation.model.ErrorType
import com.example.final_project.presentation.state.ForgotPasswordState
import com.example.final_project.presentation.util.EncryptionHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AddCardViewModel @Inject constructor(
    private val addCardUseCase: AddCardUseCase,
    private val encryptionHelper: EncryptionHelper,
    private val cardNameValidationUseCase: CardNameValidationUseCase,
    private val cardExpiryDateValidationUseCase: CardExpiryDateValidationUseCase,
    private val cardNumberValidationUseCase: CardNumberValidationUseCase,
    private val cardCvvValidationUseCase: CardCvvValidationUseCase
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<AddCardNavigationEvents>()
    val uiEvent: SharedFlow<AddCardNavigationEvents> get() = _uiEvent

    private val _errorFlow = MutableStateFlow<Int?>(null)
    val errorFlow: StateFlow<Int?> = _errorFlow.asStateFlow()

    fun onEvent(events: AddCardEvents) {
        when (events) {
            is AddCardEvents.AddCard -> addCard(
                cardNumber = events.cardNumber,
                expiryDate = events.expiryDate,
                cvv = events.cvv,
                cardName = events.cardName
            )

            is AddCardEvents.ResetErrorMessage -> {
                _errorFlow.value = null
            }
        }
    }

    private fun addCard(cardNumber: String, expiryDate: String, cvv: String, cardName: String) {
        viewModelScope.launch {
            if (validateCardNumber(cardNumber) && validateCardCvv(cvv) && validateCardExpiryDate(expiryDate) && validateCardName(cardName)) {
                addCardUseCase(
                    GetCard(id = Random(500000).nextInt(),
                        cardNumber = encryptionHelper.encrypt(cardNumber),
                        expirationDate = encryptionHelper.encrypt(expiryDate),
                        cvv = encryptionHelper.encrypt(cvv),
                        cardName = cardName)
                )

                _uiEvent.emit(AddCardNavigationEvents.NavigateBack)
            }
        }
    }

    private fun updateErrorMessage(errorMessage: Int?, errorType: ErrorType) {
        when(errorType) {
            ErrorType.CARD_NAME -> _errorFlow.value = errorMessage
            ErrorType.CARD_NUMBER -> _errorFlow.value = errorMessage
            ErrorType.CARD_CVV -> _errorFlow.value = errorMessage
            ErrorType.CARD_EXPIRY_DATE -> _errorFlow.value = errorMessage

            else -> {}
        }
    }

    private fun validateCardNumber(cardNumber: String): Boolean {
        return if (!cardNumberValidationUseCase(cardNumber)) {
            updateErrorMessage(R.string.card_number_error, ErrorType.CARD_NUMBER)
            false
        }else
            true
    }

    private fun validateCardName(cardName: String): Boolean {
        return if (!cardNameValidationUseCase(cardName)) {
            updateErrorMessage(R.string.card_name_error, ErrorType.CARD_NAME)
            false
        }else
            true
    }

    private fun validateCardExpiryDate(cardExpiryDate: String): Boolean {
        return if (!cardExpiryDateValidationUseCase(cardExpiryDate)) {
            updateErrorMessage(R.string.card_exp_date_error, ErrorType.CARD_EXPIRY_DATE)
            false
        }else
            true
    }

    private fun validateCardCvv(cardCvv: String): Boolean {
        return if (!cardCvvValidationUseCase(cardCvv)) {
            updateErrorMessage(R.string.card_cvv_error, ErrorType.CARD_CVV)
            false
        }else
            true
    }
}

sealed class AddCardEvents {
    data class AddCard(val cardNumber: String, val expiryDate: String, val cvv: String, val cardName: String) : AddCardEvents()
    object ResetErrorMessage : AddCardEvents()
}