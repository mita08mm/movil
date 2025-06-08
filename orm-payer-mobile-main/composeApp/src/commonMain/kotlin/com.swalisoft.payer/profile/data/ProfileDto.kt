package com.swalisoft.payer.profile.data

import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    val id: String,
    val name: String,
    val email: String
)

@Serializable
data class UpdateProfileDto(
    val name: String,
    val email: String
)

@Serializable
data class ChangePasswordDto(
    val currentPassword: String,
    val newPassword: String
)

@Serializable
data class ApiResponseDto(
    val message: String
)

@Serializable
data class ApiErrorDto(
    val message: String
) 