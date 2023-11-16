package com.santimattius.module.shared.domain.command

interface CommandHandler<in C : Command> {

    fun handle(command: C)
}