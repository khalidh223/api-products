package com.shopping.products.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(value="product")
data class Product (
    @Id
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val price: Long
)

data class ProductRequest(
    val name: String,
    val description: String,
    val price: Long
)

data class ProductResponse(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val price: Long
)