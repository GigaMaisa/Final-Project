package com.example.final_project.presentation.state

import com.example.final_project.presentation.model.Contact

data class ContactsState(
    val contacts: List<Contact>? = null,
    val isLoading: Boolean = false,
    val errorMessage: Int? = null
)
