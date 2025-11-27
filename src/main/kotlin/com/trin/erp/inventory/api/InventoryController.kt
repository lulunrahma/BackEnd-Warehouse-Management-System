package com.trin.erp.inventory.api

import com.trin.erp.inventory.domain.InventoryService
import com.trin.erp.inventory.domain.Item
import io.ktor.http.*
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put

class InventoryController(private val inventoryService: InventoryService) {

    fun Route.registerInventoryRoutes() {
        // Endpoint untuk menambah item
        post("/items") {
            val item = call.receive<Item>()
            inventoryService.addItem(item)
            call.respond(HttpStatusCode.Created, item)
        }

        // Endpoint untuk mengambil daftar item
        get("/items") {
            val items = inventoryService.getAllItems()
            call.respond(HttpStatusCode.OK, items)
        }

        // Endpoint untuk mengambil detail item berdasarkan ID
        get("/items/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val item = inventoryService.getItemById(id)
                if (item != null) {
                    call.respond(HttpStatusCode.OK, item)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Item not found")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid item ID")
            }
        }

        // Endpoint untuk memperbarui item
        put("/items/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val updatedItem = call.receive<Item>()
                updatedItem.id = id
                inventoryService.updateItem(updatedItem)
                call.respond(HttpStatusCode.OK, updatedItem)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid item ID")
            }
        }

        // Endpoint untuk menghapus item
        delete("/items/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                inventoryService.removeItem(id)
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid item ID")
            }
        }
    }
}
