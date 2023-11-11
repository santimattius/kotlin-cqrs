package com.example.module.product.domain

class ProductSearchService(
    private val repository: ProductRepository
) {

    suspend fun search(id: ProductId): Result<Product> {
        return repository.find(id)
    }
}