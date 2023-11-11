package com.example

import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import org.junit.Test
import kotlin.test.assertEquals

class HealthCheckTest {

    @Test
    fun validateHealthCheck() = integrationTest {

        testConfigure()

        execute { client ->
            val response = client.get("/healthcheck")
            assertEquals(HttpStatusCode.OK, response.status)
        }
    }
}