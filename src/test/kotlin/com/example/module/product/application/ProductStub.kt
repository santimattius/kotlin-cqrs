package com.example.module.product.application

import com.example.module.product.domain.Product
import com.example.module.product.domain.ProductId
import com.example.module.product.domain.ProductName
import com.example.module.product.domain.ProductPrice
import java.util.*
import kotlin.random.Random

/**
 * create random product id
 */
fun stubProductId() = ProductId(UUID.randomUUID().toString())

/**
 * create random product name
 */
fun stubProductName() = ProductName(value = "Product ${Random.nextInt()}")

/**
 * create random product price
 */
fun stubProductPrice() = ProductPrice(Random.nextDouble(from = 100.0, until = 1000.0))

fun stubProduct(
    productId: ProductId = stubProductId(),
    productName: ProductName = stubProductName(),
    productPrice: ProductPrice = stubProductPrice()
) = Product(productId, productName, productPrice)
