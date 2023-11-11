package com.example.module.product.application

import com.example.module.product.domain.Product
import com.example.module.product.domain.ProductRepository

class ProductCatalog(
    private val repository: ProductRepository
) {

    suspend fun list(): Result<List<Product>> {
        return Result.success(repository.all())
    }
}