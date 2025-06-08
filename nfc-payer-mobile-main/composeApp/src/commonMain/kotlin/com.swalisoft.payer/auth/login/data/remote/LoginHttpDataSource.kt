package com.swalisoft.payer.auth.login.data.remote

import com.swalisoft.payer.AppConfig
import com.swalisoft.payer.auth.login.data.mapper.ApiConflictResponse
import com.swalisoft.payer.auth.login.data.mapper.ApiResponse
import com.swalisoft.payer.auth.login.data.mapper.LoginRequestDto
import com.swalisoft.payer.auth.login.data.mapper.LoginSuccessMapper
import com.swalisoft.payer.auth.login.data.mapper.UserRequestDto
import com.swalisoft.payer.common.exception.HttpConflictException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class LoginHttpDataSource(
  private val httpClient: HttpClient,
) {
  suspend fun login(credentials: LoginRequestDto): LoginSuccessMapper {
    val url = "${AppConfig.API_URL}/auth/login"

    val response = httpClient.post(url) {
      contentType(ContentType.Application.Json)
      setBody(credentials)
    }

    when (response.status) {
      HttpStatusCode.OK -> {
        return response.body<LoginSuccessMapper>()
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

  suspend fun register(user: UserRequestDto) {
    val url = "${AppConfig.API_URL}/auth/register"

    val response = httpClient.post(url) {
      contentType(ContentType.Application.Json)
      setBody(user)
    }

    when (response.status) {
      HttpStatusCode.Created -> {
        val body = response.body<ApiResponse>()
        println(body)
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