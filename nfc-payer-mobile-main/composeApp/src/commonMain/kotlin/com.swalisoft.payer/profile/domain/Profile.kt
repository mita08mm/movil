package com.swalisoft.payer.profile.domain

data class Profile(
    val id: String,
    val name: String,
    val email: String
)

data class UpdateProfile(
    val name: String,
    val email: String
)

data class ChangePassword(
    val currentPassword: String,
    val newPassword: String
) 