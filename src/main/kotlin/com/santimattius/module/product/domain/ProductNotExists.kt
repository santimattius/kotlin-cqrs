package com.santimattius.module.product.domain

class ProductNotExists(
    id: String,
    exception: String = "ProductNotExists"
) : Throwable("Product $id no found ($exception)")

