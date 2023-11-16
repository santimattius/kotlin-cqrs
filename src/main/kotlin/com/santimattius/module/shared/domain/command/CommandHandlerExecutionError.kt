package com.santimattius.module.shared.domain.command

class CommandHandlerExecutionError(override val cause: Throwable?) : RuntimeException()