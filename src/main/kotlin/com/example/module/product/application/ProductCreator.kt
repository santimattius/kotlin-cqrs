package com.example.module.product.application

import com.example.module.product.domain.ProductId
import com.example.module.product.domain.ProductName
import com.example.module.product.domain.ProductPrice
import com.example.module.product.domain.ProductRepository
import com.example.module.product.domain.Product


class ProductCreator(
    private val repository: ProductRepository
) {

    suspend fun create(id: ProductId, name: ProductName, price: ProductPrice): Result<Product> {
        val product = Product.create(id, name, price)
        return repository.save(product)
    }
}