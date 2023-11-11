package com.example.configurations

import com.example.configurations.plugins.configureKoin
import com.example.configurations.plugins.configureRouting
import com.example.configurations.plugins.configureSerialization
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

/*legacy mode*/
fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    configureKoin()
    configureRouting()
    configureSerialization()
}
