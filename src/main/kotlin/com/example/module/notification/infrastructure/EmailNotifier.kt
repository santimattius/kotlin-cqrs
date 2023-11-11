package com.example.module.notification.infrastructure

import com.example.module.notification.domain.NotificationText
import com.example.module.notification.domain.NotificationType
import com.example.module.notification.domain.Notifier
import com.external.gmail.Email
import com.external.gmail.GmailClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private const val GMAIL_DELAY = 2_000L
/**
 * Notifier adapter for Gmail implementation
 */
class EmailNotifier(
    user: String,
    password: String,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : Notifier {

    private val gmailClient = GmailClient(user, password)

    override suspend fun notify(text: NotificationText, type: NotificationType): Result<String> {
        val email = Email("hardcoded-subject", text())
        return withContext(dispatcher) {
            delay(GMAIL_DELAY)
            gmailClient.send(email)
            Result.success("Email")
        }
    }
}