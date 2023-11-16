package com.santimattius.module.product.application.create

import com.santimattius.module.product.domain.ProductId
import com.santimattius.module.product.domain.ProductName
import com.santimattius.module.product.domain.ProductPrice
import com.santimattius.module.product.domain.ProductRepository
import com.santimattius.module.product.domain.Product


class ProductCreator(
    private val repository: ProductRepository
) {

    suspend fun create(id: ProductId, name: ProductName, price: ProductPrice): Result<Product> {
        val product = Product.create(id, name, price)
        return repository.save(product)
    }
}