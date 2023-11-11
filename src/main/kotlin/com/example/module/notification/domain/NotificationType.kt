package com.example.module.notification.domain

enum class NotificationType {
    UNKNOWN,
    CREATED,
    UPDATED,
    REMOVED;

    companion object {
        fun from(value: String): NotificationType {
            return values().firstOrNull { it.name == value.toUpperCase() } ?: UNKNOWN
        }
    }
}

