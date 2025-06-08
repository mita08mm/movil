package com.swalisoft.payer.notification.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class NotificationDto(
    val id: String,
    @SerialName("message") val body: String,
    val read: Boolean,
    @SerialName("date") val createdAt: String
) 