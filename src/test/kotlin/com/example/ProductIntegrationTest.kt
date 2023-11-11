package com.example

import com.example.module.product.domain.ProductRepository
import com.example.module.product.infrastructure.Product
import com.example.module.product.infrastructure.repositories.InMemoryProductRepository
import com.example.module.product.infrastructure.repositories.stubProductDto
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import org.mockito.kotlin.mock
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProductIntegrationTest {

    @Test
    fun getProductsWithProducts() = integrationTest {

        testConfigure {
            single<ProductRepository> { FakeProductRepository() }
        }

        execute { client ->
            val response = client.get("/v1/product/all")
            assertEquals(HttpStatusCode.OK, response.status)
            assertEquals(response.data<List<Product>>().isNotEmpty(), true)
        }
    }

    @Test
    fun getProductWithValidId() = integrationTest {

        testConfigure {
            single<ProductRepository> { FakeProductRepository() }
        }

        val id = UUID.randomUUID().toString()

        execute { client ->
            val response = client.get("/v1/product/${id}")
            assertEquals(HttpStatusCode.OK, response.status)
            assertEquals(response.data<Product>().id, id)
        }
    }

    @Test
    fun postProductWhenProductCreated() = legacyIntegrationTest {
        testConfigure {
            single<ProductRepository> { InMemoryProductRepository() }
        }

        val response = post("/v1/product") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            body(Product(name = "Test Product", price = 200.0))
        }

        assertEquals(HttpStatusCode.Created, response.status())
        assertEquals(200.0, response.data<Product>()?.price)
    }

    @Test
    fun postProductWhenProductBadRequest() = legacyIntegrationTest {
        testConfigure {
            single<ProductRepository> { InMemoryProductRepository() }
        }

        val response = post("/v1/product") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            body(Product(id = "", name = "", price = 0.0))
        }

        assertEquals(HttpStatusCode.BadRequest, response.status())
    }

    @Test
    fun postProductWhenFailProductCreation() = legacyIntegrationTest {
        //Mock repository with save fail
        val repository: ProductRepository = mock {
            onBlocking {
                save(org.mockito.kotlin.any())
            }.thenReturn(Result.failure(Exception()))
        }

        testConfigure {
            single<ProductRepository> { repository }
        }

        val response = post("/v1/product") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            body(Product(name = "Test Product", price = 200.0))
        }

        assertEquals(HttpStatusCode.BadRequest, response.status())
    }

    @Test
    fun putProductWhenProductCreated() = legacyIntegrationTest {

        testConfigure {
            single<ProductRepository> { InMemoryProductRepository() }
        }

        val responsePost = post("/v1/product") {
            header(ContentType.Application.Json)
            body(Product(name = "Test Product", price = 200.0))
        }
        val productCreated = responsePost.data<Product>()
        assertTrue(productCreated != null)

        val productUpdated = productCreated.copy(price = 500.0)

        val responsePut = put("/v1/product") {
            header(ContentType.Application.Json)
            body(productUpdated)
        }

        val productUpdate = responsePut.data<Product>()
        assertTrue(productUpdate != null)
        assertEquals(500.0, productUpdate.price)
    }

    @Test
    fun putProductWhenProductNoExistsBadRequest() = legacyIntegrationTest {

        testConfigure {
            single<ProductRepository> { InMemoryProductRepository() }
        }

        val productCreated = stubProductDto()

        val productUpdated = productCreated.copy(price = 500.0)

        val responsePut = put("/v1/product") {
            header(ContentType.Application.Json)
            body(productUpdated)
        }

        assertEquals(HttpStatusCode.BadRequest, responsePut.status())
    }

    @Test
    fun putProductWhenProductBadRequest() = legacyIntegrationTest {

        testConfigure {
            single<ProductRepository> { InMemoryProductRepository() }
        }

        val responsePost = post("/v1/product") {
            header(ContentType.Application.Json)
            body(Product(name = "Test Product", price = 200.0))
        }
        val productCreated = responsePost.data<Product>()
        assertTrue(productCreated != null)

        val productUpdated = productCreated.copy(name = " ", price = 0.0)

        val responsePut = put("/v1/product") {
            header(ContentType.Application.Json)
            body(productUpdated)
        }
        assertEquals(HttpStatusCode.BadRequest, responsePut.status())
    }

}





