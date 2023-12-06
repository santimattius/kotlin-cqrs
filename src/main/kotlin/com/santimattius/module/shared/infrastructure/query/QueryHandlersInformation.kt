package com.santimattius.module.shared.infrastructure.query

import com.santimattius.module.shared.domain.query.Query
import com.santimattius.module.shared.domain.query.QueryHandler
import com.santimattius.module.shared.domain.query.QueryNotRegisteredError
import org.reflections.Reflections
import java.lang.reflect.ParameterizedType

class QueryHandlersInformation {
    private var indexedQueryHandlers: HashMap<Class<out Query?>, Class<out QueryHandler<*, *>>>

    init {
        val reflections = Reflections("com.santimattius")
        val classes = reflections.getSubTypesOf(
            QueryHandler::class.java
        )

        indexedQueryHandlers = formatHandlers(classes)
    }

    @Throws(QueryNotRegisteredError::class)
    fun search(queryClass: Class<out Query?>): Class<out QueryHandler<*, *>> {
        val queryHandlerClass = indexedQueryHandlers[queryClass] ?: throw QueryNotRegisteredError(queryClass)

        return queryHandlerClass
    }

    private fun formatHandlers(
        queryHandlers: Set<Class<out QueryHandler<*, *>>>
    ): HashMap<Class<out Query?>, Class<out QueryHandler<*, *>>> {
        val handlers: HashMap<Class<out Query?>, Class<out QueryHandler<*, *>>> = HashMap()
        for (handler in queryHandlers) {
            val paramType = handler.genericInterfaces[0] as ParameterizedType
            val queryClass: Class<out Query?> = paramType.actualTypeArguments[0] as Class<out Query?>

            handlers[queryClass] = handler
        }

        return handlers
    }
}