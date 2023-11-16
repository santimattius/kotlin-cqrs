package com.santimattius.module.shared.domain.command

import kotlin.jvm.Throws

interface CommandBus {

    @Throws(CommandHandlerExecutionError::class)
    fun dispatch(command: Command)
}