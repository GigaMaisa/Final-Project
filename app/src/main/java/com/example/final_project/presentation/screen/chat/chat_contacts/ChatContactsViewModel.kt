package com.example.final_project.presentation.screen.chat.chat_contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.usecase.chat.AddContactUseCase
import com.example.final_project.domain.usecase.chat.GetContactsUseCase
import com.example.final_project.presentation.event.chat.ChatContactEvent
import com.example.final_project.presentation.mapper.chat.toDomain
import com.example.final_project.presentation.mapper.chat.toPresentation
import com.example.final_project.presentation.model.chat.Contact
import com.example.final_project.presentation.state.ContactsState
import com.example.final_project.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatContactsViewModel @Inject constructor(private val getContactsUseCase: GetContactsUseCase, private val addContactUseCase: AddContactUseCase): ViewModel() {
    private val _contactsStateFlow = MutableStateFlow(ContactsState())
    val contactsStateFlow = _contactsStateFlow.asStateFlow()

    fun onEvent(event: ChatContactEvent) {
        when(event) {
            is ChatContactEvent.GetContactsEvent -> getContacts()
            is ChatContactEvent.AddContactEvent -> addContact(contact = event.contact)
        }
    }

    private fun getContacts() {
        viewModelScope.launch {
            getContactsUseCase().collect {resource ->
                when(resource) {
                    is Resource.Loading -> _contactsStateFlow.update { currentState -> currentState.copy(isLoading = resource.loading) }
                    is Resource.Success -> _contactsStateFlow.update { currentState -> currentState.copy(contacts = resource.response.map { it.toPresentation() }) }
                    is Resource.Error -> updateErrorMessage(getErrorMessage(resource.error))
                }
            }
        }
    }

    private fun addContact(contact: Contact) {
        viewModelScope.launch {
            addContactUseCase(contact = contact.toDomain())
        }
    }

    private fun updateErrorMessage(errorMessage: Int?) {
        _contactsStateFlow.update { currentState -> currentState.copy(errorMessage = errorMessage) }
    }
}