package com.trin.erp.inventory.api

import com.trin.erp.inventory.domain.InventoryService
import com.trin.erp.inventory.domain.StockMovement
import com.trin.erp.inventory.domain.MovementType
import com.trin.erp.inventory.domain.Item
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.*
import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import org.koin.test.KoinTest
import org.koin.java.KoinJavaComponent.inject
import kotlin.test.Test
import kotlin.test.assertEquals

class MovementControllerTest : KoinTest {

    // Inject the InventoryService from Koin
    private val inventoryService by inject<InventoryService>()

    // This function sets up Ktor testing environment
    fun Application.testModule() {
        // Initialize Ktor routes here
        movementRoutes() // This should register routes for stock movement handling
    }

    @Test
    fun `should transfer stock correctly`() = withTestApplication(Application::testModule) {
        // Create a new item and add it to the inventory service
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 20, locationId = 1)
        inventoryService.addItem(item)

        // Create a new StockMovement instance
        val movement = StockMovement(
            id = 1,
            itemId = 1,
            type = MovementType.TRANSFER,
            quantity = 10,
            date = "2025-11-26",
            sourceLocationId = 1,
            destLocationId = 2
        )

        // Send a POST request to the "/stock-movements" endpoint with the stock movement data
        handleRequest(HttpMethod.Post, "/stock-movements") {
            addHeader("Content-Type", "application/json")
            setBody("""
                {
                    "id": 1,
                    "itemId": 1,
                    "type": "TRANSFER",
                    "quantity": 10,
                    "date": "2025-11-26",
                    "sourceLocationId": 1,
                    "destLocationId": 2
                }
            """)
        }.apply {
            // Check the response status and content
            assertEquals(HttpStatusCode.Created, response.status())
            assertEquals("""
                {"id":1,"itemId":1,"type":"TRANSFER","quantity":10,"date":"2025-11-26","sourceLocationId":1,"destLocationId":2}
            """, response.content)
        }
    }
}
