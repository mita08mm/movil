package com.swalisoft.payer.auth.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swalisoft.payer.auth.login.domain.LoginModel
import com.swalisoft.payer.auth.login.domain.LoginRepository
import com.swalisoft.payer.auth.login.domain.RegisterModel
import com.swalisoft.payer.common.exception.HttpException
import com.swalisoft.payer.common.getStorageManager
import eus.ctb.barik.common.helper.ToastManagerHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
  private val authRepository: LoginRepository,
): ViewModel() {
  private val _state = MutableStateFlow(LoginState())
  private val storage = getStorageManager()

  val state = _state.stateIn(
    viewModelScope,
    SharingStarted.WhileSubscribed(5000L),
    _state.value,
  )

  fun registerUser(name: String, email: String, password: String) = viewModelScope.launch {
    _state.update { it.copy(isLoading = true) }

    try {
      authRepository.register(RegisterModel(
        name = name,
        email = email,
        password = password,
      ))

      val token = authRepository.login(LoginModel(
        username = email,
        password = password,
      ))

      storage.saveAuthToken(token.accessToken)

      _state.update { it.copy(isLoading = false, successLogin = true) }
    } catch (error: HttpException) {
      println(error)

      ToastManagerHelper.showToast(error.message)
      _state.update { it.copy(isLoading = false) }
    }
  }

  fun loginUser(username: String, password: String) = viewModelScope.launch {
    _state.update { it.copy(isLoading = true) }

    try {
      val token = authRepository.login(LoginModel(
        username = username,
        password = password,
      ))

      storage.saveAuthToken(token.accessToken)

      _state.update { it.copy(isLoading = false, successLogin = true) }
    } catch (error: HttpException) {
      println(error)
      ToastManagerHelper.showToast(error.message)

      _state.update { it.copy(isLoading = false) }
    }
  }
}
