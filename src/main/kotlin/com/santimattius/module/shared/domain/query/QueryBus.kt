package com.santimattius.module.shared.domain.query

interface QueryBus {
    @Throws(QueryHandlerExecutionError::class)
    suspend fun <R> ask(query: Query): R where R : Response
}