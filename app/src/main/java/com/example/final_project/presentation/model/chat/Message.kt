package com.example.final_project.presentation.model.chat

data class Message(
    val id: Long?,
    val message: String?,
    val senderId: String?
) {
    constructor() : this(null, null, null)
}
