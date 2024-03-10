package com.example.final_project.presentation.activity

import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.UUID

object DialogflowIntegration {

     fun obtainAccessToken(credentials: String): String {
        val url = "https://oauth2.googleapis.com/token"
        val postData = "grant_type=client_credentials&client_id=CLIENT_ID&client_secret=CLIENT_SECRET"
            .replace("CLIENT_ID", JSONObject(credentials).getString("client_id"))
            .replace("CLIENT_SECRET", JSONObject(credentials).getString("client_secret"))

        val postDataBytes = postData.toByteArray(StandardCharsets.UTF_8)

        val connection = URL(url).openConnection() as HttpURLConnection
        connection.doOutput = true
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
        connection.setRequestProperty("Content-Length", postDataBytes.size.toString())
        connection.outputStream.use { it.write(postDataBytes) }

        val response = connection.inputStream.bufferedReader().use { it.readText() }
        return JSONObject(response).getString("access_token")
    }

     fun detectIntent(projectId: String, sessionId: String, textInput: String, accessToken: String): String {
        val url = "https://dialogflow.googleapis.com/v2/projects/$projectId/agent/sessions/$sessionId:detectIntent"

        val postData = """
        {
            "queryInput": {
                "text": {
                    "text": "$textInput",
                    "languageCode": "en"
                }
            }
        }
    """.trimIndent()

        val postDataBytes = postData.toByteArray(StandardCharsets.UTF_8)

        val connection = URL(url).openConnection() as HttpURLConnection
        connection.doOutput = true
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("Authorization", "Bearer $accessToken")
        connection.outputStream.use { it.write(postDataBytes) }

        val response = connection.inputStream.bufferedReader().use { it.readText() }
        return JSONObject(response).getJSONObject("queryResult").getString("fulfillmentText")
    }
}