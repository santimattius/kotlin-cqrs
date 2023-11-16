package com.santimattius.module.product.application.create

import com.santimattius.module.shared.domain.command.Command
import java.util.*

data class CreateProductCommand(
    override val commandId: UUID = UUID.randomUUID(),
    val id: String,
    val name: String,
    val price: Double
) : Command
