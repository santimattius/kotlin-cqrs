package com.santimattius.module.product.application.create

import com.santimattius.module.product.domain.ProductId
import com.santimattius.module.product.domain.ProductName
import com.santimattius.module.product.domain.ProductPrice
import com.santimattius.module.shared.domain.command.CommandHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateProductCommandHandler(
    private val productCreator: ProductCreator,
) : CommandHandler<CreateProductCommand> {


    override suspend fun handle(command: CreateProductCommand) {
        productCreator.create(
            id = ProductId(command.id),
            name = ProductName(command.name),
            price = ProductPrice(command.price)
        )
    }
}