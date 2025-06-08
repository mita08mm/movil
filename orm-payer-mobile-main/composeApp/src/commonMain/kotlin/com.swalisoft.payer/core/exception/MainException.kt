package com.swalisoft.payer.core.exception

import kotlinx.serialization.Serializable

@Serializable
data class MainException(
    val type: ExceptionType = ExceptionType.Unknown,
    val exceptionMessage: String? = null
) : Throwable()
