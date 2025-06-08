package com.swalisoft.payer.profile.data

import com.swalisoft.payer.profile.domain.Profile
import com.swalisoft.payer.profile.domain.ProfileRepository
import com.swalisoft.payer.profile.domain.UpdateProfile
import com.swalisoft.payer.profile.domain.ChangePassword

class ProfileRepositoryImpl(
    private val remote: ProfileHttpDataSource
) : ProfileRepository {
    override suspend fun getProfile(token: String): Profile {
        val dto = remote.getProfile(token)
        return Profile(dto.id, dto.name, dto.email)
    }

    override suspend fun updateProfile(token: String, update: UpdateProfile): Profile {
        val dto = remote.updateProfile(token, UpdateProfileDto(update.name, update.email))
        return Profile(dto.id, dto.name, dto.email)
    }

    override suspend fun changePassword(token: String, change: ChangePassword) {
        remote.changePassword(token, ChangePasswordDto(change.currentPassword, change.newPassword))
    }

    override suspend fun deleteProfile(token: String) {
        remote.deleteProfile(token)
    }
} 