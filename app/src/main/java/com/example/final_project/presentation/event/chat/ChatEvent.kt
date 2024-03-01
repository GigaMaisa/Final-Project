package com.example.final_project.presentation.event.chat

import com.example.final_project.presentation.model.Message

sealed interface ChatEvent {
    class GetMessagesEvent(val receiverUuid: String): ChatEvent
    class AddMessageEvent(val message: Message, val receiverUuid: String): ChatEvent
}
