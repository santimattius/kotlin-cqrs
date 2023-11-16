package com.santimattius

import com.santimattius.module.product.application.stubProduct
import com.santimattius.module.product.domain.Product
import com.santimattius.module.product.domain.ProductId
import com.santimattius.module.product.domain.ProductRepository

class FakeProductRepository : ProductRepository {

    override suspend fun all(): List<Product> = listOf(stubProduct())

    override suspend fun find(productId: ProductId) = Result.success(stubProduct(productId))

    override suspend fun save(product: Product) = Result.success(product)

    override suspend fun update(product: Product) = Result.success(product)

}
