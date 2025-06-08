package com.swalisoft.payer.auth.login.domain

import com.swalisoft.payer.auth.login.data.mapper.LoginSuccessMapper

interface LoginRepository {
  suspend fun login(user: LoginModel): LoginSuccessMapper

  suspend fun register(user: RegisterModel)
}
