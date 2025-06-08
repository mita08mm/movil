package com.swalisoft.payer.profile.data

import com.swalisoft.payer.AppConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class ProfileHttpDataSource(
    private val httpClient: HttpClient
) {
    suspend fun getProfile(token: String): ProfileDto {
        val url = "${AppConfig.API_URL}/profile"
        val response = httpClient.get(url) {
            contentType(ContentType.Application.Json)
            headers.append("Authorization", "Bearer $token")
        }
        if (response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            val error = response.body<ApiErrorDto>()
            throw Exception(error.message)
        }
    }

    suspend fun updateProfile(token: String, update: UpdateProfileDto): ProfileDto {
        val url = "${AppConfig.API_URL}/profile"
        val response = httpClient.put(url) {
            contentType(ContentType.Application.Json)
            headers.append("Authorization", "Bearer $token")
            setBody(update)
        }
        if (response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            val error = response.body<ApiErrorDto>()
            throw Exception(error.message)
        }
    }

    suspend fun changePassword(token: String, change: ChangePasswordDto) {
        val url = "${AppConfig.API_URL}/profile/password"
        val response = httpClient.put(url) {
            contentType(ContentType.Application.Json)
            headers.append("Authorization", "Bearer $token")
            setBody(change)
        }
        if (response.status != HttpStatusCode.OK) {
            val error = response.body<ApiErrorDto>()
            throw Exception(error.message)
        }
    }

    suspend fun deleteProfile(token: String) {
        val url = "${AppConfig.API_URL}/profile"
        val response = httpClient.delete(url) {
            contentType(ContentType.Application.Json)
            headers.append("Authorization", "Bearer $token")
        }
        if (response.status != HttpStatusCode.OK) {
            val error = response.body<ApiErrorDto>()
            throw Exception(error.message)
        }
    }
} 