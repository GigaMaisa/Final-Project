package com.example.final_project.domain.usecase.validators

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class CardExpiryDateValidationUseCase @Inject constructor() {
    operator fun invoke(input: String): Boolean {
        return true
        // TODO
    }
}