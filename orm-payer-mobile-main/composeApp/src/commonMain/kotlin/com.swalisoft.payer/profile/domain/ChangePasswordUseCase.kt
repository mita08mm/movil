package com.swalisoft.payer.profile.domain
 
class ChangePasswordUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(token: String, change: ChangePassword) {
        repository.changePassword(token, change)
    }
} 