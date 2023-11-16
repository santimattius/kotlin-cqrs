package com.santimattius.module.notification.application

import com.santimattius.module.notification.domain.NotificationText
import com.santimattius.module.notification.domain.NotificationType

/**
 * create random notification type
 */
fun stubNotificationType() = NotificationType.entries.toTypedArray().random()

/**
 * create random notification text
 */
fun stubNotificationText() = NotificationText(value = "This is notification text content")