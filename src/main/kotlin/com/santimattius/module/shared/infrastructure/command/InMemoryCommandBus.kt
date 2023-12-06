package com.santimattius.module.shared.infrastructure.command

import com.santimattius.module.shared.domain.command.Command
import com.santimattius.module.shared.domain.command.CommandBus
import com.santimattius.module.shared.domain.command.CommandHandler
import com.santimattius.module.shared.domain.command.CommandHandlerExecutionError
import org.koin.core.component.KoinComponent
import org.koin.java.KoinJavaComponent


class InMemoryCommandBus(
    private val information: CommandHandlersInformation
) : CommandBus, KoinComponent {


    @Suppress("TooGenericExceptionCaught")
    @Throws(CommandHandlerExecutionError::class)
    override suspend fun dispatch(command: Command) {
        try {
            val commandHandlerClass = information.search(command.javaClass)
            val handler: CommandHandler<Command> = KoinJavaComponent.get(commandHandlerClass)
            handler.handle(command)
        } catch (error: Throwable) {
            throw CommandHandlerExecutionError(error)
        }
    }
}