package com.example.module.product.domain

data class Product(
    val id: ProductId,
    val name: ProductName,
    val price: ProductPrice
) {

    fun updateName(name: ProductName) = copy(name = name)

    fun updatePrice(price: ProductPrice) = copy(price = price)

    companion object {
        /**
         * named constructor
         */
        fun create(id: ProductId, name: ProductName, price: ProductPrice): Product {
            return Product(id, name, price)
        }
    }
}



