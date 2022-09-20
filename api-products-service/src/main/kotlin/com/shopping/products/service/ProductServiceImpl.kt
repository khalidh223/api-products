package com.shopping.products.service

import com.shopping.products.model.Product
import com.shopping.products.model.ProductRequest
import com.shopping.products.model.ProductResponse
import com.shopping.products.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {
    override fun createProduct(productRequest: ProductRequest) {
        val product = Product(name = productRequest.name, description = productRequest.description, price = productRequest.price)
        productRepository.save(product)
    }

    override fun getAllProducts(): List<ProductResponse> {
        val products = productRepository.findAll()
        return products.map { ProductResponse(id = it.id, name = it.name, description = it.description, price = it.price) }
    }

}