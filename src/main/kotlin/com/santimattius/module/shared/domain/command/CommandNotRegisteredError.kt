package com.santimattius.module.shared.domain.command

class CommandNotRegisteredError(command: Class<out Command?>?) :
    Exception(String.format("The command <%s> hasn't a command handler associated", command.toString()))