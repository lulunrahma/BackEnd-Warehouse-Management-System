package com.trin.erp.inventory.api

import com.trin.erp.inventory.domain.InventoryService
import com.trin.erp.inventory.domain.StockMovement
import com.trin.erp.inventory.domain.MovementType
import com.trin.erp.inventory.domain.Item
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import org.junit.Test
import kotlin.test.assertEquals

class MovementControllerTest {

    @Test
    fun `should transfer stock correctly`() = testApplication {
        val inventoryService = InventoryService()
        // Create a new item and add it to the inventory service
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 20, locationId = 1)
        inventoryService.addItem(item)

        application {
            routing {
                movementRoutes(inventoryService)
            }
        }

        // Send a POST request to the "/stock-movements" endpoint with the stock movement data
        val response = client.post("/stock-movements") {
            contentType(io.ktor.http.ContentType.Application.Json)
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
        }

        // Check the response status and content
        assertEquals(HttpStatusCode.Created, response.status)
    }
}
