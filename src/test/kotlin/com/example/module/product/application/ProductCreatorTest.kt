package com.example.module.product.application

import com.example.module.product.domain.Product
import com.example.module.product.domain.ProductRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProductCreatorTest {
    private val repository: ProductRepository = mock()
    private val productCreator = ProductCreator(repository)

    @Test
    fun `verify save product when create action`() {

        val id = stubProductId()
        val name = stubProductName()
        val price = stubProductPrice()
        val product = Product.create(id, name, price)

        runBlocking {
            `when`(repository.save(product))
                .thenReturn(Result.success(product))

            val result = productCreator.create(id, name, price)

            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull(), product)

            verify(repository).save(product)
        }
    }
}