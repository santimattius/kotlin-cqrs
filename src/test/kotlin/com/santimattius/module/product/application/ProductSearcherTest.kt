package com.santimattius.module.product.application

import com.santimattius.module.product.domain.ProductNotExists
import com.santimattius.module.product.domain.ProductRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsInstanceOf
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class ProductSearcherTest {

    private val repository: ProductRepository = mock()
    private val productSearcher = ProductSearcher(repository)

    @Test
    fun `verify invoke search from repository when product exists`() {
        runBlocking {
            //Given
            val productId = stubProductId()
            val product = stubProduct(productId)

            `when`(repository.find(productId))
                .thenReturn(Result.success(product))

            //When
            val result = productSearcher.search(productId)

            //Then
            assertThat(result.isSuccess, IsEqual(true))
            assertThat(result.getOrNull(), IsEqual(product))
        }
    }

    @Test
    fun `verify invoke search from repository when product no exists`() {
        runBlocking {
            //Given
            val productId = stubProductId()

            `when`(repository.find(productId))
                .thenReturn(Result.failure(ProductNotExists(productId())))

            //When
            val result = productSearcher.search(productId)

            //Then
            assertThat(result.isFailure, IsEqual(true))
            assertThat(result.exceptionOrNull(), IsInstanceOf(ProductNotExists::class.java))
        }
    }

}