package com.swalisoft.payer.todo.data

import com.swalisoft.payer.AppConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class TaskHttpDataSource(
    private val httpClient: HttpClient
) {
    suspend fun getTasks(token: String): List<TaskDto> {
        println("TOKEN PARA GET TASKS: $token")
        val url = "${AppConfig.API_URL}/tasks"
        val response = httpClient.get(url) {
            contentType(ContentType.Application.Json)
            headers.append("Authorization", "Bearer $token")
        }
        println("STATUS: ${response.status}")
        try {
            val bodyText = response.bodyAsText()
            println("BODY: $bodyText")
        } catch (e: Exception) {
            println("No se pudo obtener bodyAsText: ${e.message}")
        }
        if (response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            throw Exception("Error al obtener tareas")
        }
    }

    suspend fun createTask(token: String, dto: TaskCreateDto): TaskDto {
        val url = "${AppConfig.API_URL}/tasks"
        val response = httpClient.post(url) {
            contentType(ContentType.Application.Json)
            headers.append("Authorization", "Bearer $token")
            setBody(dto)
        }
        if (response.status == HttpStatusCode.Created) {
            return response.body()
        } else {
            throw Exception("Error al crear tarea")
        }
    }

    suspend fun updateTask(token: String, id: String, dto: TaskDto): TaskDto {
        val url = "${AppConfig.API_URL}/tasks/$id"
        val response = httpClient.patch(url) {
            contentType(ContentType.Application.Json)
            headers.append("Authorization", "Bearer $token")
            setBody(dto)
        }
        if (response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            throw Exception("Error al actualizar tarea")
        }
    }

    suspend fun deleteTask(token: String, id: String) {
        val url = "${AppConfig.API_URL}/tasks/$id"
        val response = httpClient.delete(url) {
            contentType(ContentType.Application.Json)
            headers.append("Authorization", "Bearer $token")
        }
        if (response.status != HttpStatusCode.NoContent) {
            throw Exception("Error al eliminar tarea")
        }
    }
} 