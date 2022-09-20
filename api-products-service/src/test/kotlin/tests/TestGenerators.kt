package tests

import com.shopping.products.model.Product
import com.shopping.products.model.ProductRequest
import io.kotlintest.properties.Gen

fun Gen.Companion.productRequest() = object : Gen<ProductRequest> {
    override fun constants(): Iterable<ProductRequest> = emptyList()

    override fun random(): Sequence<ProductRequest> = generateSequence {
        ProductRequest(
            name = string().random().first().take(6),
            description = string().random().first().take(10),
            price = long().random().first()
        )
    }
}
fun Gen.Companion.product() = object : Gen<Product> {
    override fun constants(): Iterable<Product> = emptyList()

    override fun random(): Sequence<Product> = generateSequence {
        Product(
            name = string().random().first().take(6),
            description = string().random().first().take(10),
            price = long().random().first()
        )
    }
}