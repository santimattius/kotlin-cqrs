package com.santimattius.module.notification.infrastructure

import com.santimattius.module.notification.application.NotificationSender
import com.santimattius.module.notification.domain.NotificationText
import com.santimattius.module.notification.domain.NotificationType

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