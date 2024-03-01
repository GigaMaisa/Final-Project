package com.example.final_project.domain.usecase.signup

import javax.inject.Inject

data class UserAdditionalDataUseCase @Inject constructor(
    val addUserFullNameUseCase: AddUserFullNameUseCase,
    val addUserEmailUseCase: AddUserEmailUseCase,
    val addUserPasswordUseCase: AddUserPasswordUseCase,
    val getUserUuidUseCase: GetUserUuidUseCase
)
