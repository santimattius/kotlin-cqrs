package com.santimattius.module.shared.infrastructure.command

import com.santimattius.module.shared.domain.command.Command
import com.santimattius.module.shared.domain.command.CommandHandler
import com.santimattius.module.shared.domain.command.CommandNotRegisteredError
import org.reflections.Reflections
import java.lang.reflect.ParameterizedType


class CommandHandlersInformation {
    private var indexedCommandHandlers: HashMap<Class<out Command>, Class<out CommandHandler<*>>> = hashMapOf()

    init {
        load()
    }

    private fun load() {
        val reflections = Reflections("com.santimattius")
        val classes = reflections.getSubTypesOf(
            CommandHandler::class.java
        )

        indexedCommandHandlers = formatHandlers(classes)
    }

    @Throws(CommandNotRegisteredError::class)
    fun search(commandClass: Class<out Command?>): Class<out CommandHandler<*>> {
        val commandHandlerClass =
            indexedCommandHandlers[commandClass] ?: throw CommandNotRegisteredError(commandClass)

        return commandHandlerClass
    }

    private fun formatHandlers(
        commandHandlers: Set<Class<out CommandHandler<*>>>
    ): HashMap<Class<out Command?>, Class<out CommandHandler<*>>> {
        val handlers = HashMap<Class<out Command?>, Class<out CommandHandler<*>>>()

        for (handler in commandHandlers) {
            val paramType = handler.genericInterfaces[0] as ParameterizedType
            val commandClass = paramType.actualTypeArguments[0] as Class<out Command?>

            handlers[commandClass] = handler
        }

        return handlers
    }
}