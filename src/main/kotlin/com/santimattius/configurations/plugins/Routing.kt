package com.santimattius.configurations.plugins

import com.santimattius.configurations.routes.healthCheck
import com.santimattius.configurations.routes.notificationV1
import com.santimattius.configurations.routes.productV1
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.configureRouting() {

    routing {
        healthCheck()
        notificationV1()
        productV1()
    }
}
