package com.example.final_project.presentation.screen.card_add.fragment

import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.final_project.databinding.FragmentAddCardBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.AddCardNavigationEvents
import com.example.final_project.presentation.extension.showSnackBar
import com.example.final_project.presentation.screen.card_add.viewmodel.AddCardEvents
import com.example.final_project.presentation.screen.card_add.viewmodel.AddCardViewModel
import com.example.final_project.presentation.util.getErrorMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddCardFragment : BaseFragment<FragmentAddCardBinding>(FragmentAddCardBinding::inflate) {
    private val addCardViewModel: AddCardViewModel by viewModels()

    private lateinit var cardNumberEditText: AppCompatEditText
    private lateinit var expiryDateEditText: AppCompatEditText
    private lateinit var cvvEditText: AppCompatEditText

    override fun setUp() = with(binding) {
        cardNumberEditText = etCardNumber
        expiryDateEditText = etExpiryDate
        cvvEditText = etCvv

        setExpiryDateSeparator()
        setCardNumberSeparator()


    }

    override fun setUpListeners() = with(binding) {
        btnAddCard.setOnClickListener {
            addCardViewModel.onEvent(AddCardEvents.AddCard(
                cardNumber = etCardNumber.text.toString(),
                expiryDate = etExpiryDate.text.toString(),
                cvv = etCvv.text.toString(),
                cardName = etCardName.text.toString()
            ))
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    addCardViewModel.uiEvent.collect {
                        handleNavigationEvent(event = it)
                    }
                }

                launch {
                    addCardViewModel.errorFlow.collect {errorCode ->
                        errorCode?.let {
                            view?.showSnackBar(getString(it))
                            addCardViewModel.onEvent(AddCardEvents.ResetErrorMessage)
                        }
                    }
                }
            }
        }
    }

    private fun handleNavigationEvent(event: AddCardNavigationEvents) {
        when (event) {
            is AddCardNavigationEvents.NavigateBack -> findNavController().navigateUp()
        }
    }

    private fun setExpiryDateSeparator() {
        val separator = "/"

        expiryDateEditText.doAfterTextChanged {
            if (it?.length == 2 && it.isNotEmpty() && it[it.length - 1] != '/') {
                it.insert(2, separator)
            }

            if (it.toString().length == 5) {
                cvvEditText.requestFocus()
            }
        }
    }

    private fun setCardNumberSeparator() {
        val separator = " "
        var isFormatting = false

        cardNumberEditText.doAfterTextChanged {
            isFormatting = true

            val str = it.toString().replace(separator, "")
            val formatted = StringBuilder()
            var i = 0

            while (i < str.length) {
                formatted.append(str.substring(i, kotlin.math.min(i + 4, str.length)))
                if (i + 4 < str.length) {
                    formatted.append(separator)
                }
                i += 4
            }

            if (it.toString() != formatted.toString()) {
                cardNumberEditText.setText(formatted)
                cardNumberEditText.setSelection(formatted.length)
            }

            if (it.toString().length == 19) {
                expiryDateEditText.requestFocus()
            }

            isFormatting = false
        }
    }
}