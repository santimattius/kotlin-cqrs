package com.santimattius.module.shared.domain.command

import kotlin.jvm.Throws

interface CommandBus {

    @Throws(CommandHandlerExecutionError::class)
    suspend fun dispatch(command: Command)
}