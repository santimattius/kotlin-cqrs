package com.example.module.product.infrastructure.repositories

import com.example.module.product.infrastructure.Product
import java.util.UUID
import kotlin.random.Random


fun stubProductEntity(id: String = UUID.randomUUID().toString()) = Product(
    id = id,
    name = "Product ${Random.nextInt()}",
    price = Random.nextDouble(from = 100.0, until = 1000.0)
)

fun stubProductDto(id: String = UUID.randomUUID().toString()) = Product(
    id = id,
    name = "Product ${Random.nextInt()}",
    price = Random.nextDouble(from = 100.0, until = 1000.0)
)