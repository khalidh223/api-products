package com.shopping.products.service

import com.shopping.products.model.ProductRequest
import com.shopping.products.model.ProductResponse

interface ProductService {
    fun createProduct(productRequest: ProductRequest)
    fun getAllProducts() : List<ProductResponse>
}