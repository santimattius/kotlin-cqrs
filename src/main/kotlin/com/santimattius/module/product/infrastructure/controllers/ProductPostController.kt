package com.santimattius.module.product.infrastructure.controllers

import com.santimattius.module.product.application.create.CreateProductCommand
import com.santimattius.module.product.infrastructure.Product
import com.santimattius.module.shared.domain.command.CommandBus
import io.ktor.http.*

class ProductPostController(
    private val commandBus: CommandBus
) {

    fun post(body: Product): Pair<HttpStatusCode, Product?> {
        return try {
            val result = CreateProductCommand(
                id = body.id,
                name = body.name,
                price = body.price,
            )
            commandBus.dispatch(result)
            return HttpStatusCode.Created to null
        } catch (ex: IllegalArgumentException) {
            HttpStatusCode.BadRequest to null
        }
    }
}


