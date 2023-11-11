package com.example.configurations.plugins

import com.example.configurations.routes.healthCheck
import com.example.configurations.routes.notificationV1
import com.example.configurations.routes.productV1
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.configureRouting() {

    routing {
        healthCheck()
        notificationV1()
        productV1()
    }
}
