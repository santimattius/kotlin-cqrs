package com.example.configurations.routes

import com.example.configurations.plugins.inject
import com.example.module.healthcheck.HealthCheckController
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get

fun Routing.healthCheck() {
    getHealthCheck()
}

fun Route.getHealthCheck() {

    val controller: HealthCheckController by inject()

    get("/healthcheck") {
        val result = controller.get()
        val responseCode = if (result.isSuccess) HttpStatusCode.OK else HttpStatusCode.BadRequest
        call.respondText(status = responseCode, text = result.getOrNull() ?: "Service not found")
    }
}

