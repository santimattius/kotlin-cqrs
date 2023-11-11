package com.example.configurations.plugins


import com.example.configurations.container.dependencies
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.module.Module

fun Application.configureKoin(modules: List<Module> = emptyList()) {
    install(KoinPlugin) {
        modules(dependencies + modules)
    }
}