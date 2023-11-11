package com.example.module.product.infrastructure

import com.example.module.product.domain.Product
import com.example.module.product.domain.ProductId
import com.example.module.product.domain.ProductName
import com.example.module.product.domain.ProductPrice
import com.example.module.product.infrastructure.Product as ProductEntity

internal fun MutableList<ProductEntity>.asDomainProduct(): List<Product> {
    return this.map { it.asDomainProduct() }
}

internal fun ProductEntity.asDomainProduct() = Product(
    id = ProductId(value = this.id),
    name = ProductName(value = this.name),
    price = ProductPrice(value = this.price)
)

internal fun Product.asEntity(): ProductEntity {
    return ProductEntity(
        id = this.id(),
        name = this.name(),
        price = this.price(),
    )
}

fun Product.asDTO(): ProductEntity {
    return this.asEntity()
}

fun List<Product>.asDTOs(): List<ProductEntity> {
    return map { it.asDTO() }
}