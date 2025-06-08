package com.swalisoft.payer.profile.domain

class GetProfileUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(token: String): Profile {
        return repository.getProfile(token)
    }
} 