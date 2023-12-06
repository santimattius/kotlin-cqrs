package com.santimattius.module.product.application.search

import com.santimattius.module.product.domain.ProductId
import com.santimattius.module.shared.domain.query.QueryHandler

class ProductSearchQueryHandler(
    private val productSearcher: ProductSearcher
) : QueryHandler<ProductSearchQuery, ProductResponse> {
    override suspend fun handle(query: ProductSearchQuery): ProductResponse {
        val product = productSearcher.search(ProductId(query.id)).getOrThrow()
        return ProductResponse(product)
    }
}