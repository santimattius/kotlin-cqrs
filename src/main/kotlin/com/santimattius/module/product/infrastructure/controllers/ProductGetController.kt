package com.santimattius.module.product.infrastructure.controllers

import com.santimattius.module.product.application.ProductCatalog
import com.santimattius.module.product.application.search.ProductResponse
import com.santimattius.module.product.application.search.ProductSearchQuery
import com.santimattius.module.product.application.search.ProductSearcher
import com.santimattius.module.product.infrastructure.Product
import com.santimattius.module.product.infrastructure.asDTO
import com.santimattius.module.product.infrastructure.asDTOs
import com.santimattius.module.shared.infrastructure.ApiController
import io.ktor.http.*

class ProductGetController(
    private val productCatalog: ProductCatalog
) : ApiController() {

    suspend fun get(id: String): Pair<HttpStatusCode, Product?> {
        val result = runCatching<ProductResponse> {
            ask(ProductSearchQuery(id))
        }
        return result.fold(
            onSuccess = { HttpStatusCode.OK to it.data.asDTO() },
            onFailure = { HttpStatusCode.BadRequest to null }
        )
    }


    suspend fun get(): Pair<HttpStatusCode, List<Product>> {
        val result = productCatalog.list()
        return HttpStatusCode.OK to result.getOrDefault(emptyList()).asDTOs()
    }
}