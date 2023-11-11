package com.example.module.notification.infrastructure

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    @SerialName("text") val text: String,
    @SerialName("type") val type: String
)
