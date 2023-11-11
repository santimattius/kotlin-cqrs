package com.example.module.product.application

import com.example.module.product.domain.ProductRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsSame
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock


class ProductCatalogTest {

    private val productRepository = mock<ProductRepository>()
    private val productCatalog = ProductCatalog(productRepository)

    @Test
    fun `result is empty when product catalog is empty`() = runBlocking {
        //Given
        `when`(productRepository.all())
            .thenReturn(emptyList())

        //When
        val products = productCatalog.list()

        //Then
        assertThat(products.isSuccess, IsEqual(true))
        assertThat(products.getOrNull(), IsSame(emptyList()))
    }
}