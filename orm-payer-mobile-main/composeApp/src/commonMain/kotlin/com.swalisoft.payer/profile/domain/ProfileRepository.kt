package com.swalisoft.payer.profile.domain

interface ProfileRepository {
    suspend fun getProfile(token: String): Profile
    suspend fun updateProfile(token: String, update: UpdateProfile): Profile
    suspend fun changePassword(token: String, change: ChangePassword)
    suspend fun deleteProfile(token: String)
} 