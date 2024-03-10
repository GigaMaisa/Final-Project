package com.example.final_project.data.remote.service

import com.example.final_project.data.remote.common.Resource
import com.example.final_project.data.remote.model.ChatBotRequestDto
import com.example.final_project.data.remote.model.ChatBotResponseDto
import com.example.final_project.domain.model.home.GetBanner
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatBotApiService {
    @Headers(
        "Authorization: Bearer ya29.a0Ad52N39oPAbnxumMF2zNvp99CZ5USK9DOWGhaPuVKSbA0FDP9dlm1n_m8PnpZZiXzlLgufjSMNBhEqT99unRsfexmNhWOjUt5igZAwovit8dn7_P_1JI2qFAPnPuIkgT5WwLfAW9qmw8_gkP5KFBAuVSghwagOsunm6GaCgYKAY0SARMSFQHGX2MiuDa-x-NknV-9QSxMyILttg0171",
        "x-goog-user-project: finalproject-4ba60"
    )
    @POST("/v2/projects/finalproject-4ba60/agent/sessions/{sessionId}:detectIntent")
    suspend fun postRequest(@Path("sessionId") sessionId: String, @Body body: ChatBotRequestDto) : Response<ChatBotResponseDto>
}