package com.swalisoft.payer.auth.login.data.mapper

import com.swalisoft.payer.auth.login.domain.LoginModel
import com.swalisoft.payer.auth.login.domain.RegisterModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
  @SerialName("message") val message: String,
)

@Serializable
data class ApiConflictResponse(
  @SerialName("message") val message: List<String>,
)

@Serializable
data class LoginSuccessMapper(
  @SerialName("accessToken") val accessToken: String,
)

@Serializable
data class LoginRequestDto(
  @SerialName("email") val email: String,
  @SerialName("password") val password: String,
)

@Serializable
data class UserRequestDto(
  @SerialName("email") val email: String,
  @SerialName("name") val name: String,
  @SerialName("password") val password: String,
)

fun LoginModel.toDto() = LoginRequestDto(
  email = username,
  password = password,
)

fun RegisterModel.toDto() = UserRequestDto(
  name = name,
  email = email,
  password = password,
)
