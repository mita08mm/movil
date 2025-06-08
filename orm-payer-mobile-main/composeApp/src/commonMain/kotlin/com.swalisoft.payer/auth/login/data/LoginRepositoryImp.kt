package com.swalisoft.payer.auth.login.data

import com.swalisoft.payer.auth.login.data.mapper.LoginSuccessMapper
import com.swalisoft.payer.auth.login.data.mapper.toDto
import com.swalisoft.payer.auth.login.data.remote.LoginHttpDataSource
import com.swalisoft.payer.auth.login.domain.LoginModel
import com.swalisoft.payer.auth.login.domain.LoginRepository
import com.swalisoft.payer.auth.login.domain.RegisterModel

class LoginRepositoryImp(
  private val datasource: LoginHttpDataSource
) : LoginRepository {
  override suspend fun login(user: LoginModel): LoginSuccessMapper {
    val response = datasource.login(user.toDto())

    return response
  }

  override suspend fun register(user: RegisterModel) {
    datasource.register(user.toDto())
  }
}