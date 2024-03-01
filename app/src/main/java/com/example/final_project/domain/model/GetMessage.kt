package com.example.final_project.domain.model

data class GetMessage(
    val id: Long?,
    val message: String?,
    val senderId: String?
) {
    constructor() : this(null, null, null)
}