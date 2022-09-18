package com.shopping.products.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(value="product")
data class Product (
    @Id
    val id: Int = 0,
    val name: String,
    val description: String,
    val price: Long
)