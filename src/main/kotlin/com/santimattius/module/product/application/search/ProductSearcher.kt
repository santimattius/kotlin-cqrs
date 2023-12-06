package com.santimattius.module.product.application.search

import com.santimattius.module.product.domain.*

class ProductSearcher(
    private val repository: ProductRepository
) {

    private val productSearchService = ProductSearchService(repository)
    suspend fun search(id: ProductId): Result<Product> {
        return productSearchService.search(id)
    }

}