package com.example.module.product.application

import com.example.module.product.domain.Product
import com.example.module.product.domain.ProductId
import com.example.module.product.domain.ProductNotExists
import com.example.module.product.domain.ProductRepository

class ProductSearcher(
    private val repository: ProductRepository
) {

    // <editor-fold defaultstate="collapsed" desc="Primera implemetación">
    suspend fun search(id: ProductId): Result<Product> {
        val result = repository.find(id)
        return result.fold(
            onSuccess = { product -> Result.success(product) },
            onFailure = { Result.failure(ProductNotExists(id())) }
        )
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Usando servicios de dominio">
//    private val productSearchService = ProductSearchService(repository)
//    suspend fun search(id: ProductId): Result<Product> {
//        return productSearchService.search(id)
//    }
    // </editor-fold>

}