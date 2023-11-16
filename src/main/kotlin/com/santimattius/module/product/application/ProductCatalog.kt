package com.santimattius.module.product.application

import com.santimattius.module.product.domain.Product
import com.santimattius.module.product.domain.ProductRepository

class ProductCatalog(
    private val repository: ProductRepository
) {

    suspend fun list(): Result<List<Product>> {
        return Result.success(repository.all())
    }
}