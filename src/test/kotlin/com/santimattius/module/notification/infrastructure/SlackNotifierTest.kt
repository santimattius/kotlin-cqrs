package com.santimattius.module.notification.infrastructure

import com.santimattius.module.notification.application.stubNotificationText
import com.santimattius.module.notification.application.stubNotificationType
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Test

class SlackNotifierTest {

    @Test
    fun testNotify() {
        runBlocking {

            val notifier = SlackNotifier(hookUrl = "slack/test")

            val result = notifier.notify(stubNotificationText(), stubNotificationType())

            assertThat(result.isSuccess, IsEqual(true))
            assertThat(result.getOrNull(), IsEqual("Slack"))
        }
    }
}