package com.example.module.notification.infrastructure

import com.example.module.notification.application.NotificationSender
import com.example.module.notification.domain.NotificationText
import com.example.module.notification.domain.NotificationType

class NotificationPostController(
    private val notificationSender: NotificationSender
) {

    suspend fun post(body: Notification): Result<String> {
        return try {
            notificationSender(
                text = NotificationText(body.text),
                type = NotificationType.from(body.type)
            )
        } catch (ex: IllegalArgumentException) {
            Result.failure(ex)
        }
    }
}