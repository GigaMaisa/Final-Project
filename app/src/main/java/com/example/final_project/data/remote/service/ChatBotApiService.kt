package com.example.final_project.data.remote.service

import com.example.final_project.data.remote.model.ChatBotRequestDto
import com.example.final_project.data.remote.model.ChatBotResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatBotApiService {
    @Headers(
        "Authorization: Bearer ya29.a0Ad52N38QU6ociNvWkY8gVWEALtUFbv6ixnC2Mt6UEZleUVZP-I3NoXR9Ta2yBXUx3ensBJy4KzBntMv-ZOAHDYfnf3sm6BBuug0mkchqhItBUtYWSOv2z8MSvZ6geS2QFZA2LAsGpAqaGLwLEjlgbPlxTCnT7KdG3FnGaCgYKAUMSARMSFQHGX2MiBv5jmzC5YiQ6v4fBHZEVvw0171",
        "x-goog-user-project: finalproject-4ba60"
    )
    @POST("/v2/projects/finalproject-4ba60/agent/sessions/{sessionId}:detectIntent")
    suspend fun postRequest(@Path("sessionId") sessionId: String, @Body body: ChatBotRequestDto) : Response<ChatBotResponseDto>
}