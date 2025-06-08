package com.swalisoft.payer.profile.domain
 
class UpdateProfileUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(token: String, update: UpdateProfile): Profile {
        return repository.updateProfile(token, update)
    }
} 