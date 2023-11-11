package com.example.configurations.routes

import com.example.configurations.plugins.inject
import com.example.module.notification.infrastructure.Notification
import com.example.module.notification.infrastructure.NotificationPostController
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post


fun Routing.notificationV1() {
    postNotification()
}

fun Route.postNotification() {

    val controller: NotificationPostController by inject()

    post("/v1/notification") {
        val notification = call.receive<Notification>()
        val result = controller.post(notification)
        val responseCode = if (result.isSuccess) HttpStatusCode.Created else HttpStatusCode.BadRequest
        call.respondText(status = responseCode, text = result.getOrNull() ?: "Notification not found")
    }
}

