package com.example.final_project.presentation.screen.chat.chat_contacts

import androidx.lifecycle.ViewModel
import com.example.final_project.presentation.model.Contact
import com.example.final_project.presentation.state.ContactsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChatContactsViewModel : ViewModel() {
    val contacts = listOf(
        Contact("8GfWhKHhLGdmyE3cQxRzDkIvtBB3", "SHAKO LOMTADZE"),
        Contact("agivv52GYgZkIfp95NVGoXIuxAH2", "BRUH BRUH")
    )

    private val _contactsStateFlow = MutableStateFlow(ContactsState(contacts = contacts))
    val contactsStateFlow = _contactsStateFlow.asStateFlow()
}