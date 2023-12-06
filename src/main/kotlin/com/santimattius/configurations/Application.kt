package com.santimattius.configurations

import com.santimattius.configurations.plugins.configureKoin
import com.santimattius.configurations.plugins.configureRouting
import com.santimattius.configurations.plugins.configureSerialization
import com.santimattius.module.shared.domain.command.CommandBus
import com.santimattius.module.shared.domain.query.QueryBus
import com.santimattius.module.shared.infrastructure.command.CommandHandlersInformation
import com.santimattius.module.shared.infrastructure.command.InMemoryCommandBus
import com.santimattius.module.shared.infrastructure.query.InMemoryQueryBus
import com.santimattius.module.shared.infrastructure.query.QueryHandlersInformation
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
