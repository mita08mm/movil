package com.swalisoft.payer.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swalisoft.payer.profile.domain.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Estado simple para la UI de perfil
data class ProfileUiState(
    val name: String = "User",
    val email: String = "user@email.com",
    val notificationsEnabled: Boolean = true
)

class ProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val deleteProfileUseCase: DeleteProfileUseCase,
    private val tokenProvider: suspend () -> String // función para obtener el token actual
): ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun loadProfile() {
        viewModelScope.launch {
            val token = tokenProvider()
            try {
                val profile = getProfileUseCase(token)
                _uiState.update { it.copy(name = profile.name, email = profile.email) }
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun onEditProfile(newName: String, newEmail: String) {
        viewModelScope.launch {
            val token = tokenProvider()
            try {
                val updated = updateProfileUseCase(token, UpdateProfile(newName, newEmail))
                _uiState.update { it.copy(name = updated.name, email = updated.email) }
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun onChangePassword(current: String, new: String) {
        viewModelScope.launch {
            val token = tokenProvider()
            try {
                changePasswordUseCase(token, ChangePassword(current, new))
                // Mostrar éxito
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun onDeleteAccount() {
        viewModelScope.launch {
            val token = tokenProvider()
            try {
                deleteProfileUseCase(token)
                // Navegar fuera de la app o logout
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun onToggleNotifications(enabled: Boolean) {
        _uiState.update { it.copy(notificationsEnabled = enabled) }
    }
} 