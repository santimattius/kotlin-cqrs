package com.santimattius.module.shared.infrastructure

import com.santimattius.module.shared.domain.command.Command
import com.santimattius.module.shared.domain.command.CommandBus
import com.santimattius.module.shared.domain.query.Query
import com.santimattius.module.shared.domain.query.QueryBus
import com.santimattius.module.shared.domain.query.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class ApiController : KoinComponent {

    private val commandBus by inject<CommandBus>()
    private val queryBus by inject<QueryBus>()


    protected suspend fun <R, Q> ask(query: Q): R where Q : Query, R : Response {
        return queryBus.ask(query)
    }

    protected suspend fun <C> dispatch(command: C) where C : Command {
        commandBus.dispatch(command)
    }

}