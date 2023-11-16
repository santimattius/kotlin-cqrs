package com.santimattius.module.notification.domain

import com.santimattius.module.shared.isNotEmptyOrNotBlack


@JvmInline
value class NotificationText(private val value: String) {

    init {
        require(value.isNotEmptyOrNotBlack()) {
            "Notification text is required."
        }
    }

    operator fun invoke() = value
}
