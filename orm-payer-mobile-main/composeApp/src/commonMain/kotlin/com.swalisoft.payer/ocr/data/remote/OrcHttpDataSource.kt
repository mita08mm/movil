package com.swalisoft.payer.ocr.data.remote

import com.swalisoft.payer.AppConfig
import com.swalisoft.payer.ocr.data.mapper.ApiConflictResponse
import com.swalisoft.payer.ocr.data.mapper.ApiResponse
import com.swalisoft.payer.common.exception.HttpConflictException
import com.swalisoft.payer.common.getStorageManager
import com.swalisoft.payer.note.data.dto.NoteDto
import com.swalisoft.payer.ocr.data.mapper.OrcRequestDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode

class OrcHttpDataSource(
  private val httpClient: HttpClient,
) {
  suspend fun processImage(ocr: OrcRequestDto): NoteDto {
    val url = "${AppConfig.API_URL}/ocr/process-image"
    val token = getStorageManager().getAuthToken()

    val response = httpClient.submitFormWithBinaryData(
      url,
      formData {
        append(key = "image", ocr.image, Headers.build {
          append(HttpHeaders.ContentType, "image/jpg")
          append(HttpHeaders.ContentDisposition, "filename=\"${"orc_image.jpg"}\"")
        })
        append(key = "categoryId", ocr.categoryId)
      }
    ) {
      headers {

        if(token != null){
          append(HttpHeaders.Authorization, "Bearer $token")
        }
      }
    }

    when (response.status) {
      HttpStatusCode.Created -> {
        return response.body<NoteDto>()
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

  suspend fun getSingleNote(noteId: String): NoteDto {
    val url = "${AppConfig.API_URL}/notes/$noteId"

    val token = getStorageManager().getAuthToken()

    val response = httpClient.get(url) {
      headers {
        if(token != null){
          append(HttpHeaders.Authorization, "Bearer $token")
        }
      }
    }

    when (response.status) {
      HttpStatusCode.Created -> {
        return response.body<NoteDto>()
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
}
