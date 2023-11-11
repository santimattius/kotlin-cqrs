package com.example.module.product.domain

import com.example.module.product.application.stubProduct
import com.example.module.product.application.stubProductId
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsInstanceOf
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ProductSearchServiceTest {

    private val repository: ProductRepository = mock()
    private val productSearcher = ProductSearchService(repository)

    @Test
    fun `verify invoke search from repository when product exists`() {
        runBlocking {
            //Given
            val productId = stubProductId()
            val product = stubProduct(productId)

            Mockito.`when`(repository.find(productId))
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

            Mockito.`when`(repository.find(productId))
                .thenReturn(Result.failure(ProductNotExists(productId())))

            //When
            val result = productSearcher.search(productId)

            //Then
            assertThat(result.isFailure, IsEqual(true))
            assertThat(result.exceptionOrNull(), IsInstanceOf(ProductNotExists::class.java))
        }
    }
}