package com.swalisoft.payer.notification.domain


interface NotificationRepository {
    suspend fun getNotifications(): Result<List<NotificationModel>>
    suspend fun markAsRead(id: String): Result<Unit>
    suspend fun deleteNotification(id: String): Result<Unit>
}
