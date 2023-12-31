package com.santimattius.module.product.application

import com.santimattius.module.product.domain.ProductId
import com.santimattius.module.product.domain.ProductName
import com.santimattius.module.product.domain.ProductRepository
import com.santimattius.module.product.domain.ProductSearchService
import com.santimattius.module.product.domain.ProductPrice
import com.santimattius.module.product.domain.Product


class ProductUpdater(
    private val repository: ProductRepository
) {

    private val productSearcher = ProductSearchService(repository)

    suspend fun update(id: ProductId, name: ProductName, price: ProductPrice): Result<Product> {
        val result = productSearcher.search(id)
        return result.fold(
            onSuccess = { oldProduct ->
                val productUpdated = oldProduct
                    .updateName(name)
                    .updatePrice(price)

                repository.update(productUpdated)
            },
            onFailure = { exception ->
                Result.failure(exception)
            }
        )
    }

}
