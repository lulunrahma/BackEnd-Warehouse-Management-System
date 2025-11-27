package com.trin.erp.inventory.api

import com.trin.erp.inventory.domain.InventoryService
import com.trin.erp.inventory.domain.Item
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import org.junit.Test
import kotlin.test.assertEquals

class ItemControllerTest {

    @Test
    fun `should add item correctly`() = testApplication {
        val inventoryService = InventoryService()

        application {
            routing {
                inventoryRoutes(inventoryService)
            }
        }

        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 10, locationId = 1)

        val response = client.post("/items") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody("""{"id": 1, "sku": "SKU123", "name": "Item A", "quantity": 10, "locationId": 1}""")
        }

        assertEquals(HttpStatusCode.Created, response.status)
    }

    @Test
    fun `should get all items`() = testApplication {
        val inventoryService = InventoryService()
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 10, locationId = 1)
        inventoryService.addItem(item)

        application {
            routing {
                inventoryRoutes(inventoryService)
            }
        }

        val response = client.get("/items")
        assertEquals(HttpStatusCode.OK, response.status)
    }
}
