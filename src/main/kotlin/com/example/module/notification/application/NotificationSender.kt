package com.example.module.notification.application

import com.example.module.notification.domain.NotificationText
import com.example.module.notification.domain.NotificationType
import com.example.module.notification.domain.Notifier

class NotificationSender(
    private val notifier: Notifier
) {

    suspend operator fun invoke(
        text: NotificationText,
        type: NotificationType
    ): Result<String> {
        return notifier.notify(text, type)
    }
}