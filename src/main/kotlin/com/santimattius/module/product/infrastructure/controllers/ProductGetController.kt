package com.santimattius.module.product.infrastructure.controllers

import com.santimattius.module.product.application.ProductCatalog
import com.santimattius.module.product.application.ProductSearcher
import com.santimattius.module.product.domain.ProductId
import com.santimattius.module.product.infrastructure.Product
import com.santimattius.module.product.infrastructure.asDTO
import com.santimattius.module.product.infrastructure.asDTOs
import io.ktor.http.HttpStatusCode

class ProductGetController(
    private val productSearcher: ProductSearcher,
    private val productCatalog: ProductCatalog
) {

    suspend fun get(id: String): Pair<HttpStatusCode, Product?> {
        val result = productSearcher.search(
            id = ProductId(id)
        )
        return result.fold(
            onSuccess = { HttpStatusCode.OK to it.asDTO() },
            onFailure = { HttpStatusCode.BadRequest to null }
        )
    }


    suspend fun get(): Pair<HttpStatusCode, List<Product>> {
        val result = productCatalog.list()
        return HttpStatusCode.OK to result.getOrDefault(emptyList()).asDTOs()
    }
}