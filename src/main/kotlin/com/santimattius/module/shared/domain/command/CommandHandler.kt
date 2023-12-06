package com.santimattius.module.shared.domain.command

interface CommandHandler<in C : Command> {

    suspend fun handle(command: C)
}