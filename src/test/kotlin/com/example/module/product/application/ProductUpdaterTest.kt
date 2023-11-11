package com.example.module.product.application

import com.example.module.product.domain.ProductNotExists
import com.example.module.product.domain.ProductPrice
import com.example.module.product.domain.ProductRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsInstanceOf
import org.hamcrest.core.IsNot
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify


class ProductUpdaterTest {

    private val repository: ProductRepository = mock()
    private val productUpdater = ProductUpdater(repository)

    @Test
    fun `verify update product with new attributes when product exists`() {
        runBlocking {
            //Given
            val productId = stubProductId()
            val oldProductPrice = ProductPrice(value = 100.0)
            val oldProduct = stubProduct(productId = productId, productPrice = oldProductPrice)

            val newProductPrice = ProductPrice(value = 20.0)
            val productUpdated = oldProduct.copy(price = newProductPrice)

            `when`(repository.find(productId))
                .thenReturn(Result.success(oldProduct))

            `when`(repository.update(productUpdated))
                .thenReturn(Result.success(productUpdated))

            //When
            val result = productUpdater.update(oldProduct.id, oldProduct.name, newProductPrice)

            //Then
            //Assert price
            assertThat(result.getOrNull()?.price, IsEqual(newProductPrice))
            assertThat(result.getOrNull()?.price, IsNot(IsEqual(oldProductPrice)))
            //Assert all product
            assertThat(result.getOrNull(), IsNot(IsEqual(oldProduct)))

            verify(repository, times(1)).update(productUpdated)
        }
    }

    @Test
    fun `verify update product with new attributes when product no exists`() {
        runBlocking {
            //Given
            val productId = stubProductId()
            val oldProductPrice = ProductPrice(value = 100.0)
            val oldProduct = stubProduct(productId = productId, productPrice = oldProductPrice)

            val newProductPrice = ProductPrice(value = 20.0)

            `when`(repository.find(productId))
                .thenReturn(Result.failure(ProductNotExists(productId())))

            //When
            val result = productUpdater.update(oldProduct.id, oldProduct.name, newProductPrice)

            //Then
            assertThat(result.isFailure, IsEqual(true))
            assertThat(result.exceptionOrNull(), IsInstanceOf(ProductNotExists::class.java))

            verify(repository, times(0)).update(any())
        }
    }
}