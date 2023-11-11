package com.example.module.notification.infrastructure

import com.example.module.notification.application.stubNotificationText
import com.example.module.notification.application.stubNotificationType
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Test

class EmailNotifierTest {

    @Test
    fun testNotify() {
        runBlocking {

            val notifier = EmailNotifier(user = "test", password = "password")

            val result = notifier.notify(stubNotificationText(), stubNotificationType())

            assertThat(result.isSuccess, IsEqual(true))
            assertThat(result.getOrNull(), IsEqual("Email"))
        }
    }
}