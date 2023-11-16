package com.santimattius.configurations.plugins


import com.santimattius.configurations.container.dependencies
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.module.Module

fun Application.configureKoin(modules: List<Module> = emptyList()) {
    install(KoinPlugin) {
        modules(dependencies + modules)
    }
}