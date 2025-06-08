package com.swalisoft.payer.core.common

import com.swalisoft.payer.core.exception.MainException

sealed class ResultState<out T : Any> {

  data class Success<out T : Any>(val data: T) : ResultState<T>()
  data class Error(val exception: MainException) : ResultState<Nothing>()
  object Loading : ResultState<Nothing>()
  object Idle : ResultState<Nothing>()

  override fun toString(): String {
    return when (this) {
      is Success<*> -> "Success[data=$data]"
      is Error -> "Error[exception=$exception]"
      else -> "Loading[]"
    }
  }
}