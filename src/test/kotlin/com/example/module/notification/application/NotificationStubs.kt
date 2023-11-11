package com.example.module.notification.application

import com.example.module.notification.domain.NotificationText
import com.example.module.notification.domain.NotificationType

/**
 * create random notification type
 */
fun stubNotificationType() = NotificationType.values().random()

/**
 * create random notification text
 */
fun stubNotificationText() = NotificationText(value = "This is notification text content")