package com.example.module.notification.domain

import com.example.module.shared.isNotEmptyOrNotBlack


@JvmInline
value class NotificationText(private val value: String) {

    init {
        require(value.isNotEmptyOrNotBlack()) {
            "Notification text is required."
        }
    }

    operator fun invoke() = value
}
