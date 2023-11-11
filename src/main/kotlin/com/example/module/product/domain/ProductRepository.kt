package com.example.module.product.domain

/**
 * Port
 */
interface ProductRepository {

    suspend fun all(): List<Product>

    suspend fun find(productId: ProductId): Result<Product>

    suspend fun save(product: Product): Result<Product>

    suspend fun update(product: Product): Result<Product>
}