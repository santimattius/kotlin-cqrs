package com.santimattius.module.notification.application

import com.santimattius.module.notification.domain.NotificationText
import com.santimattius.module.notification.domain.NotificationType
import com.santimattius.module.notification.domain.Notifier

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