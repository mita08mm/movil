package com.swalisoft.payer.notification.data

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

class NotificationHttpDataSource(
    private val httpClient: HttpClient
) {
    suspend fun getNotifications(token: String): List<NotificationDto> {
        val url = "${AppConfig.API_URL}/notifications"
        val response = httpClient.get(url) {
            contentType(ContentType.Application.Json)
            headers.append("Authorization", "Bearer $token")
        }
        if (response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            throw Exception("Error al obtener notificaciones")
        }
    }

    suspend fun markAsRead(token: String, id: String) {
        val url = "${AppConfig.API_URL}/notifications/$id/read"
        val response = httpClient.put(url) {
            contentType(ContentType.Application.Json)
            headers.append("Authorization", "Bearer $token")
        }
        if (response.status != HttpStatusCode.OK) {
            throw Exception("Error al marcar como leída")
        }
    }

    suspend fun deleteNotification(token: String, id: String) {
        val url = "${AppConfig.API_URL}/notifications/$id"
        val response = httpClient.delete(url) {
            contentType(ContentType.Application.Json)
            headers.append("Authorization", "Bearer $token")
        }
        if (response.status != HttpStatusCode.OK) {
            throw Exception("Error al eliminar notificación")
        }
    }
} 