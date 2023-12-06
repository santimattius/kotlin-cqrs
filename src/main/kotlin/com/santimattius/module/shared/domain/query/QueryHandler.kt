package com.santimattius.module.shared.domain.query

interface QueryHandler<Q : Query, R : Response> {
    suspend fun handle(query: Q): R
}