package com.santimattius.module.product.application.search

import com.santimattius.module.product.domain.Product
import com.santimattius.module.shared.domain.query.Response

data class ProductResponse(
    val data: Product
) : Response