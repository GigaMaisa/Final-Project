package com.example.final_project.domain.usecase.validators

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class CardExpiryDateValidationUseCase @Inject constructor() {
    operator fun invoke(input: String): Boolean {
        val dateFormat = SimpleDateFormat("MM/yy", Locale.US)
        dateFormat.isLenient = false

        try {
            val expiryDateObj = dateFormat.parse(input)

            val calendar = Calendar.getInstance()
            calendar.time = expiryDateObj!!
            val month = calendar.get(Calendar.MONTH) + 1

            if (month !in 1..12) {
                return false
            }

            val currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100
            val year = calendar.get(Calendar.YEAR) % 100
            if (year < currentYear) {
                return false
            }

            return true
        } catch (e: Exception) {
            return false
        }
    }
}