package com.example.module.product.infrastructure.repositories


import com.example.module.product.domain.Product
import com.example.module.product.domain.ProductId
import com.example.module.product.domain.ProductNotExists
import com.example.module.product.domain.ProductRepository
import com.example.module.product.infrastructure.asDomainProduct
import com.example.module.product.infrastructure.asEntity
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.jetbrains.annotations.TestOnly
import com.example.module.product.infrastructure.Product as ProductEntity

/**
 * Adapter
 */
class InMemoryProductRepository : ProductRepository {

    // Mutex to make writes to cached values thread-safe.
    private val latestProductMutex = Mutex()

    // Cache of the latest products
    private var productCached: MutableList<ProductEntity> = mutableListOf()

    override suspend fun all(): List<Product> {
        return latestProductMutex.withLock {
            this.productCached.asDomainProduct()
        }
    }

    override suspend fun find(productId: ProductId): Result<Product> {
        return latestProductMutex.withLock {
            val product = this.productCached.firstOrNull { it.id == productId() }
            if (product == null) {
                Result.failure(ProductNotExists(id = productId()))
            } else {
                Result.success(product.asDomainProduct())
            }
        }
    }

    override suspend fun save(product: Product): Result<Product> {
        return latestProductMutex.withLock {
            this.productCached.add(product.asEntity())
            Result.success(product)
        }
    }

    override suspend fun update(product: Product): Result<Product> {
        return latestProductMutex.withLock {
            val index = this.productCached.indexOfFirst { it.id == product.id() }
            if (index == -1) {
                Result.failure(ProductNotExists(product.id()))
            } else {
                this.productCached.removeAt(index)
                this.productCached.add(product.asEntity())
                Result.success(product)
            }
        }
    }

    @TestOnly
    fun testingInit(products: List<Product>) {
        this.productCached.addAll(products.map { it.asEntity() })
    }

    @TestOnly
    fun testingClean() {
        this.productCached.clear()
    }
}


