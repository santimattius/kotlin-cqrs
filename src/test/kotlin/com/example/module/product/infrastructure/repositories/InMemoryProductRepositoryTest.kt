package com.example.module.product.infrastructure.repositories

import com.example.module.product.application.stubProduct
import com.example.module.product.application.stubProductId
import com.example.module.product.domain.ProductId
import com.example.module.product.domain.ProductNotExists
import com.example.module.product.domain.ProductPrice
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsInstanceOf
import org.hamcrest.core.IsNot
import org.junit.BeforeClass
import org.junit.Test


/**
 * Integration test
 */
class InMemoryProductRepositoryTest {

    companion object {

        private val repository = InMemoryProductRepository()

        @BeforeClass
        @JvmStatic
        fun setupClass() {
            repository.testingInit(listOf(stubProduct()))
        }

        @BeforeClass
        @JvmStatic
        fun tearDownClass() {
            repository.testingClean()
        }
    }

    @Test
    fun all() {
        runBlocking {
            val products = repository.all()
            assertThat(products.isEmpty(), IsNot(IsEqual(true)))
        }
    }

    @Test
    fun findProductWhenExists() {
        runBlocking {
            //Given
            val productId = stubProductId()
            val product = stubProduct(productId)
            repository.save(product)
            //When
            val result = repository.find(productId)
            //Then
            assertThat(result.isSuccess, IsEqual(true))
            assertThat(result.getOrNull()?.id, IsEqual(productId))
        }
    }

    @Test
    fun findProductWhenNoExists() {
        runBlocking {
            //Given
            val productId = ProductId(value = "HardcodedId")
            //When
            val result = repository.find(productId)
            //Then
            assertThat(result.isFailure, IsEqual(true))
            assertThat(result.exceptionOrNull(), IsInstanceOf(ProductNotExists::class.java))
        }
    }

    @Test
    fun save() {
        runBlocking {
            //Given
            val product = stubProduct()
            //When
            val result = repository.save(product)
            //Then
            assertThat(result.isSuccess, IsEqual(true))
            assertThat(result.getOrNull(), IsEqual(product))
        }
    }

    @Test
    fun updateWhenProductExists() {
        runBlocking {
            //Given
            val product = stubProduct(productPrice = ProductPrice(value = 200.0))
            //When
            repository.save(product)
            val newPrice = ProductPrice(value = 100.0)
            val productUpdate = product.updatePrice(newPrice)
            val result = repository.update(productUpdate)
            //Then
            assertThat(result.isSuccess, IsEqual(true))
//            assertThat(result.getOrNull(), IsNot(IsEqual(product)))
            assertThat(result.getOrNull()?.price, IsEqual(newPrice))
        }
    }

    @Test
    fun updateWhenProductNoExists() {
        runBlocking {
            //Given
            val product = stubProduct()
            //When
            val newPrice = ProductPrice(value = 100.0)
            val productUpdate = product.updatePrice(newPrice)
            val result = repository.update(productUpdate)
            //Then
            assertThat(result.isFailure, IsEqual(true))
            assertThat(result.exceptionOrNull(), IsInstanceOf(ProductNotExists::class.java))
        }
    }
}