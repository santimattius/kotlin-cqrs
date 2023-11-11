package com.example.module.product.infrastructure.controllers

import com.example.module.product.application.ProductCatalog
import com.example.module.product.application.ProductSearcher
import com.example.module.product.domain.ProductId
import com.example.module.product.infrastructure.Product
import com.example.module.product.infrastructure.asDTO
import com.example.module.product.infrastructure.asDTOs
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