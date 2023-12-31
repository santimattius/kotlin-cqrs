package com.santimattius.module.notification.domain

interface Notifier {
    suspend fun notify(text: NotificationText, type: NotificationType): Result<String>
}