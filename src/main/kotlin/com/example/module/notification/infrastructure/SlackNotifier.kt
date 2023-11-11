package com.example.module.notification.infrastructure

import com.example.module.notification.domain.NotificationText
import com.example.module.notification.domain.NotificationType
import com.example.module.notification.domain.Notifier
import com.external.slack.Message
import com.external.slack.SlackClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private const val SLACK_DELAY = 1_000L
/**
 * Notifier adapter for Slack implementation
 */
class SlackNotifier(
    hookUrl: String,
    setting: Map<String, String> = emptyMap(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : Notifier {

    private val client = SlackClient(hookUrl, setting)

    override suspend fun notify(text: NotificationText, type: NotificationType): Result<String> {
        val message = Message(
            channel = "hardcoded-channel",
            content = text()
        )
        return withContext(dispatcher) {
            delay(SLACK_DELAY)
            client.send(message)
            Result.success("Slack")
        }
    }
}