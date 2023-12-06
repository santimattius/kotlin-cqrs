package com.santimattius.module.product.application.search

import com.santimattius.module.shared.domain.query.Query

data class ProductSearchQuery(
    val id: String
) : Query
