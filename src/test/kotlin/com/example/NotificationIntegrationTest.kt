package com.example

import com.example.module.notification.domain.Notifier
import com.example.module.notification.infrastructure.EmailNotifier
import com.example.module.notification.infrastructure.Notification
import com.example.module.notification.infrastructure.SlackNotifier
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import kotlin.test.Test
import kotlin.test.assertEquals

class NotificationIntegrationTest {

    @Test
    fun postNotificationCreated() = legacyIntegrationTest {

        testConfigure()

        val response = post("/v1/notification") {
            header(ContentType.Application.Json)
            body(Notification(text = "This is text message", type = "CREATED"))
        }

        assertEquals(HttpStatusCode.Created, response.status())
    }

    @Test
    fun postNotificationEmailCreated() = legacyIntegrationTest {

        testConfigure {
            single<Notifier> { EmailNotifier(user = "user", password = "password") }
        }

        val response = post("/v1/notification") {
            header(ContentType.Application.Json)
            body(Notification(text = "This is text message", type = "CREATED"))
        }

        assertEquals(HttpStatusCode.Created, response.status())
        assertEquals("Email", response.content)
    }

    @Test
    fun postNotificationSlackCreated() = legacyIntegrationTest {

        testConfigure {
            single<Notifier> { SlackNotifier(hookUrl = "slack/notifier/test") }
        }

        val response = post("/v1/notification") {
            header(ContentType.Application.Json)
            body(Notification(text = "This is text message", type = "CREATED"))
        }

        assertEquals(HttpStatusCode.Created, response.status())
        assertEquals("Slack", response.content)
    }

    @Test
    fun postNotificationWithEmptyText() = legacyIntegrationTest {

        testConfigure()

        val response = post("/v1/notification") {
            header(ContentType.Application.Json)
            body(Notification(text = "", type = "CREATED"))
        }

        assertEquals(HttpStatusCode.BadRequest, response.status())
    }
}