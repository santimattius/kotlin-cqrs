package com.santimattius.module.shared.infrastructure.query

import com.santimattius.module.shared.domain.query.*
import org.koin.core.component.KoinComponent
import org.koin.java.KoinJavaComponent


class InMemoryQueryBus(
    private val information: QueryHandlersInformation
) : QueryBus, KoinComponent {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun <R : Response> ask(query: Query): R {
        try {
            val queryHandlerClass = information.search(query.javaClass)
            val queryHandler: QueryHandler<Query, Response> = KoinJavaComponent.get(queryHandlerClass)
            return queryHandler.handle(query) as R
        } catch (ex: Throwable) {
            throw QueryHandlerExecutionError(ex)
        }
    }
}