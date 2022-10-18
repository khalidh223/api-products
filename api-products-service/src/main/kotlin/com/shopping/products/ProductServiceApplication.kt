package com.shopping.products

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class ProductServiceApplication

fun main(args: Array<String>) {
	runApplication<ProductServiceApplication>(*args)
}
