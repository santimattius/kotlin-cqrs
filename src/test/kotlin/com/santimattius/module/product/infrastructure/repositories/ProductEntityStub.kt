package com.santimattius.module.product.infrastructure.repositories

import com.santimattius.module.product.infrastructure.Product
import java.util.UUID
import kotlin.random.Random


fun stubProductDto(id: String = UUID.randomUUID().toString()) = Product(
    id = id,
    name = "Product ${Random.nextInt()}",
    price = Random.nextDouble(from = 100.0, until = 1000.0)
)