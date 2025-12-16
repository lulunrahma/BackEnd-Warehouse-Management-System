package com.trin.erp.inventory.api

import com.trin.erp.inventory.domain.InventoryService
import com.trin.erp.inventory.domain.Item
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.testing.*
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals

class ItemControllerTest : KoinTest {

    val inventoryService by inject<InventoryService>()

    init {
        // Initializing test environment with Koin and Ktor
    }

    fun Application.testModule() {
        inventoryRoutes() // Register routes for item handling
    }

    @Test
    fun `should add item correctly`() = withTestApplication(Application::testModule) {
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 10, locationId = 1)

        handleRequest(io.ktor.http.HttpMethod.Post, "/items") {
            addHeader("Content-Type", "application/json")
            setBody("""{"id": 1, "sku": "SKU123", "name": "Item A", "quantity": 10, "locationId": 1}""")
        }.apply {
            assertEquals(HttpStatusCode.Created, response.status())
            assertEquals("""{"id": 1, "sku": "SKU123", "name": "Item A", "quantity": 10, "locationId": 1}""", response.content)
        }
    }

    @Test
    fun `should get all items`() = withTestApplication(Application::testModule) {
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 10, locationId = 1)
        inventoryService.addItem(item)

        handleRequest(io.ktor.http.HttpMethod.Get, "/items").apply {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals("[{\"id\": 1, \"sku\": \"SKU123\", \"name\": \"Item A\", \"quantity\": 10, \"locationId\": 1}]", response.content)
        }
    }
}
