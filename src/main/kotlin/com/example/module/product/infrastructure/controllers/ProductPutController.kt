package com.example.module.product.infrastructure.controllers

import com.example.module.product.application.ProductUpdater
import com.example.module.product.domain.ProductId
import com.example.module.product.domain.ProductName
import com.example.module.product.domain.ProductPrice
import com.example.module.product.infrastructure.Product
import com.example.module.product.infrastructure.asDTO
import io.ktor.http.HttpStatusCode

class ProductPutController(
    private val productUpdater: ProductUpdater
) {

    suspend fun put(body: Product): Pair<HttpStatusCode, Product?> {
        return try {
            val result = productUpdater.update(
                id = ProductId(body.id),
                name = ProductName(body.name),
                price = ProductPrice(body.price)
            )
            result.fold(
                onSuccess = { HttpStatusCode.Created to it.asDTO() },
                onFailure = { HttpStatusCode.BadRequest to null })
        } catch (ex: IllegalArgumentException) {
            HttpStatusCode.BadRequest to null
        }
    }
}


