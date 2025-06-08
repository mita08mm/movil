package com.swalisoft.payer.notification.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swalisoft.payer.notification.domain.NotificationRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf
import com.swalisoft.payer.notification.domain.NotificationModel

class NotificationsViewModel(private val notificationRepository: NotificationRepository): ViewModel() {
    private val _notifications = mutableStateListOf<NotificationModel>()
    val notifications: List<NotificationModel> get() = _notifications

    init {
        getNotifications()
    }

    private fun getNotifications() {
        viewModelScope.launch {
            val result = notificationRepository.getNotifications()
            println("[NotificationsViewModel] Resultado de getNotifications: $result")
            if (result.isSuccess) {
                val notis = result.getOrNull() ?: emptyList()
                println("[NotificationsViewModel] Cantidad de notificaciones recibidas: ${notis.size}")
                _notifications.addAll(notis)
            } else {
                println("[NotificationsViewModel] Error al obtener notificaciones: ${result.exceptionOrNull()}")
            }
        }
    }

    fun markAsRead(id: String) {
        viewModelScope.launch {
            val result = notificationRepository.markAsRead(id)
            if (result.isSuccess) {
                refreshNotifications()
            } else {
                println("[NotificationsViewModel] Error al marcar como leída: ${result.exceptionOrNull()}")
            }
        }
    }

    fun deleteNotification(id: String) {
        viewModelScope.launch {
            val result = notificationRepository.deleteNotification(id)
            if (result.isSuccess) {
                refreshNotifications()
            } else {
                println("[NotificationsViewModel] Error al eliminar notificación: ${result.exceptionOrNull()}")
            }
        }
    }

    private fun refreshNotifications() {
        _notifications.clear()
        getNotifications()
    }
}