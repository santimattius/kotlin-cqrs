package com.santimattius.module.shared.domain.command

class CommandNotRegisteredError(command: Class<out Command?>?) :
    Exception("The command $command hasn't a command handler associated" )