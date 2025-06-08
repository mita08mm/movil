package com.swalisoft.payer.notification.data

import com.swalisoft.payer.notification.domain.NotificationRepository
import com.swalisoft.payer.notification.domain.NotificationModel
import com.swalisoft.payer.notification.data.NotificationHttpDataSource
import com.swalisoft.payer.notification.data.NotificationDto

class NotificationRepositoryImp(
    private val remote: NotificationHttpDataSource,
    private val tokenProvider: suspend () -> String
) : NotificationRepository {
    override suspend fun getNotifications(): Result<List<NotificationModel>> {
        return try {
            val token = tokenProvider()
            val dtos = remote.getNotifications(token)
            val notifications = dtos.map { it.toDomain() }
            Result.success(notifications)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun markAsRead(id: String): Result<Unit> {
        return try {
            val token = tokenProvider()
            remote.markAsRead(token, id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteNotification(id: String): Result<Unit> {
        return try {
            val token = tokenProvider()
            remote.deleteNotification(token, id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

fun NotificationDto.toDomain() = NotificationModel(
    id = id,
    title = body,
    body = body,
    read = read,
    createdAt = createdAt
)