package com.swalisoft.payer.notification.domain

data class NotificationModel(
    val id: String,
    val title: String,
    val body: String,
    val read: Boolean,
    val createdAt: String
)
