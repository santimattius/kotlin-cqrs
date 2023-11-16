package com.santimattius.module.notification.domain

enum class NotificationType {
    UNKNOWN,
    CREATED,
    UPDATED,
    REMOVED;

    companion object {
        fun from(value: String): NotificationType {
            return entries.firstOrNull { it.name == value.toUpperCase() } ?: UNKNOWN
        }
    }
}

