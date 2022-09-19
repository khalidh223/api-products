package com.shopping.products.controller

import com.shopping.products.model.ProductRequest
import com.shopping.products.model.ProductResponse
import com.shopping.products.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/product")
class ProductController(private val productService: ProductService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProduct(@RequestBody productRequest: ProductRequest) {
        return productService.createProduct(productRequest)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllProducts() : List<ProductResponse> {
        return productService.getAllProducts()
    }
}