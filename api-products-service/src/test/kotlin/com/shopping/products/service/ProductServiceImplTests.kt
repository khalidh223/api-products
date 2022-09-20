package com.shopping.products.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.shopping.products.model.Product
import com.shopping.products.model.ProductRequest
import com.shopping.products.model.ProductResponse
import com.shopping.products.repository.ProductRepository
import io.kotlintest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.matchers.numerics.shouldBeExactly
import io.kotlintest.properties.Gen
import io.kotlintest.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import tests.product
import tests.productRequest

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceImplTests (
    @Autowired private val productRepository: ProductRepository,
    @Autowired private val objectMapper: ObjectMapper,
    @Autowired private val context: WebApplicationContext
) {
    @Autowired
    private val mvc: MockMvc = (MockMvcBuilders
        .webAppContextSetup(context)
        .build())

    companion object {
        @Container
        private val mongoDBContainer: MongoDBContainer = MongoDBContainer()

        @JvmStatic
        @DynamicPropertySource
        fun setProperties(dynamicPropertyRegistry: DynamicPropertyRegistry) {
            dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl)
        }
    }

    @BeforeEach
    fun setup() {
        productRepository.deleteAll()
    }

    @Test
    fun `POST a couple unique products should return 201 Created for each and save those products in the database`() {
        val amountOfProductsAdded = 5
        for (i in 1..amountOfProductsAdded) {
            val productRequest = Gen.productRequest().random().first()

            mvc.perform(
                MockMvcRequestBuilders.post("/api/product")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(productRequest))
            )
                .andExpect(MockMvcResultMatchers.status().isCreated)
        }

        productRepository.findAll().size shouldBeExactly amountOfProductsAdded
    }

    @Test
    fun `GET all products should return the correct products saved in repository`() {
        val amountOfProductsAdded = 5
        for (i in 1..amountOfProductsAdded) {
            val product = Gen.product().random().first()
            productRepository.save(product)
        }

        val results : List<Product>  = mvc.perform(
            MockMvcRequestBuilders.get("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn().response
            .contentAsObject(objectMapper)

        results shouldHaveSize amountOfProductsAdded
        results shouldContainExactlyInAnyOrder productRepository.findAll()
    }

    private inline fun <reified T : Any> MockHttpServletResponse.contentAsObject(mapper: ObjectMapper = ObjectMapper()): T =
        mapper.readValue(this.contentAsString)
}