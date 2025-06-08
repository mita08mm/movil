package com.swalisoft.payer.note.data.remote

import com.swalisoft.payer.AppConfig
import com.swalisoft.payer.auth.login.data.mapper.ApiConflictResponse
import com.swalisoft.payer.auth.login.data.mapper.ApiResponse
import com.swalisoft.payer.common.exception.HttpConflictException
import com.swalisoft.payer.common.getStorageManager
import com.swalisoft.payer.note.data.dto.CategoryDto
import com.swalisoft.payer.note.data.dto.CreateNoteDto
import com.swalisoft.payer.note.data.dto.NoteDto
import com.swalisoft.payer.note.data.mapper.toDomain
import com.swalisoft.payer.note.domain.Note
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.client.request.setBody


class NoteHttpDataSource(
    private val httpClient: HttpClient,
    ) {
    private val storage = getStorageManager()
    suspend fun getAllNotes(categoryId: String? = null):List<Note>{
        val url = "${AppConfig.API_URL}/notes"
        val token = storage.getAuthToken()
        val response = httpClient.get(url){
            headers{
                if(token != null){
                    append("Authorization", "Bearer $token")
                }
            }
        }
         when(response.status){
            HttpStatusCode.OK -> {
                val noteDto: List<NoteDto> = response.body()
                return noteDto.map { it.toDomain() }
            }
            HttpStatusCode.BadRequest -> {
                val body = response.body<ApiConflictResponse>()
                throw HttpConflictException(message = body.message.joinToString())
            }
            else -> {
                val body = response.body<ApiResponse>()
                throw HttpConflictException(message = body.message)
            }
        }
    }
    suspend fun createNote(dto: CreateNoteDto): Note {
        val res = httpClient.post("${AppConfig.API_URL}/notes") {
            contentType(ContentType.Application.Json)
            val token = storage.getAuthToken()
            setBody(dto)
            headers{
                if (token != null){
                    append("Authorization", "Bearer $token")
                }
            }
        }
        if (res.status !in listOf(HttpStatusCode.OK, HttpStatusCode.Created))
            throw HttpConflictException(res.body<ApiResponse>().message)
        return res.body<NoteDto>().toDomain()
    }
}