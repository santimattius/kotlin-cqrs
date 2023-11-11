package com.example.module.notification.application

import com.example.module.notification.domain.Notifier
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsInstanceOf
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class NotificationSenderTest {

    private val notifier: Notifier = mock()
    private val notificationSender = NotificationSender(notifier)

    @Test
    fun `verify invoke notifier when notify with result success`() {
        runBlocking {
            //Given
            val text = stubNotificationText()
            val type = stubNotificationType()

            `when`(notificationSender(text, type))
                .thenReturn(Result.success("Mock sender"))

            //When
            val result = notificationSender(text, type)

            //Then
            assertThat(result.isSuccess, IsEqual(true))
            assertThat(result.getOrNull(), IsEqual("Mock sender"))

            verify(notifier, times(1)).notify(text, type)
        }
    }

    @Test
    fun `verify invoke notifier when notify with result failure`() {
        runBlocking {
            //Given
            val text = stubNotificationText()
            val type = stubNotificationType()

            `when`(notificationSender(text, type))
                .thenReturn(Result.failure(IllegalArgumentException("Argument required")))

            //When
            val result = notificationSender(text, type)

            //Then
            assertThat(result.isFailure, IsEqual(true))
            assertThat(result.exceptionOrNull(), IsInstanceOf(IllegalArgumentException::class.java))

            verify(notifier, times(1)).notify(text, type)
        }
    }
}