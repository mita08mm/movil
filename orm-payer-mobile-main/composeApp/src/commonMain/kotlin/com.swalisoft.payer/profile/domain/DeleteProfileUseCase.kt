package com.swalisoft.payer.profile.domain
 
class DeleteProfileUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(token: String) {
        repository.deleteProfile(token)
    }
} 