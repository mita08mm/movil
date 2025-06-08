package com.swalisoft.payer.note.data.remote

import com.swalisoft.payer.AppConfig
import com.swalisoft.payer.auth.login.data.mapper.ApiConflictResponse
import com.swalisoft.payer.auth.login.data.mapper.ApiResponse
import com.swalisoft.payer.common.exception.HttpConflictException
import com.swalisoft.payer.common.getStorageManager
import com.swalisoft.payer.note.data.dto.CategoryDto
import com.swalisoft.payer.note.data.dto.CreateCategoryDto
import com.swalisoft.payer.note.domain.Category
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import com.swalisoft.payer.note.data.mapper.toDomain
import io.ktor.client.request.headers
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class CategoryHttpDataSource(
    private val httpClient: HttpClient,
){
    private val storage = getStorageManager()
    suspend fun getAllCategories(): List<Category> {
        val url = "${AppConfig.API_URL}/categories"
        val token = storage.getAuthToken()
        val response = httpClient.get(url) {
            headers {
                if (token != null) {
                    append("Authorization", "Bearer $token")
                }
            }
        }
        when (response.status) {
            HttpStatusCode.OK -> {
                val categoriesDto: List<CategoryDto> = response.body()
                return categoriesDto.map { it.toDomain() }
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
    suspend fun createCategory(payload: CreateCategoryDto): Category {
        val url = "${AppConfig.API_URL}/categories"
        val token = storage.getAuthToken()
        val response = httpClient.post(url) {
            contentType(ContentType.Application.Json)
            setBody(payload)
            headers{
                if (token != null){
                    append("Authorization", "Bearer $token")
                }
            }
        }
         return when (response.status){
            HttpStatusCode.Created,
            HttpStatusCode.OK -> {
                val dto: CategoryDto = response.body()
                dto.toDomain()
            }
            HttpStatusCode.BadRequest -> {
                val body = response.body<ApiConflictResponse>()
                throw HttpConflictException(body.message.joinToString())
            }

            else -> {
                val body = response.body<ApiResponse>()
                throw HttpConflictException(body.message)
            }
        }
    }
    suspend fun getCategoryById(categoryId: String): Category {
        val url = "${AppConfig.API_URL}/categories/$categoryId"
        val token = storage.getAuthToken()
        val response = httpClient.get(url) {
            headers {
                if (token != null) {
                    append("Authorization", "Bearer $token")
                }
            }
        }

        when (response.status) {
            HttpStatusCode.OK -> {
                val categoryDto: CategoryDto = response.body()
                return categoryDto.toDomain()
            }
            HttpStatusCode.BadRequest -> {
                val body = response.body<ApiConflictResponse>()
                throw HttpConflictException(message = body.message.joinToString())
            }
            HttpStatusCode.NotFound -> {
                throw HttpConflictException(message = "Category not found")
            }
            else -> {
                val body = response.body<ApiResponse>()
                throw HttpConflictException(message = body.message)
            }
        }
    }
}