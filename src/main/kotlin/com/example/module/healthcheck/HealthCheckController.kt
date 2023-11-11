package com.example.module.healthcheck

import kotlinx.coroutines.delay

class HealthCheckController {

    suspend fun get(): Result<String> {
        delay(DELAY_CHECK)
        return Result.success("Services running!")
    }

    companion object {
        private const val DELAY_CHECK = 1_000L
    }
}