package com.santimattius.configurations

import com.santimattius.configurations.plugins.configureKoin
import com.santimattius.configurations.plugins.configureRouting
import com.santimattius.configurations.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

/*legacy mode*/
fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureKoin()
    configureRouting()
}
