package com.trin.erp.inventory.api

import com.trin.erp.inventory.domain.InventoryService
import com.trin.erp.inventory.domain.Item
import com.trin.erp.inventory.domain.StockMovement
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put

fun Route.inventoryRoutes(inventoryService: InventoryService) {
    post("/items") {
        val item = call.receive<Item>()
        inventoryService.addItem(item)
        call.respond(HttpStatusCode.Created, item)
    }

    get("/items") {
        println("Request received for /items") // Log tambahan
        val items = inventoryService.getAllItems()
        call.respond(HttpStatusCode.OK, items)
    }

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

fun Route.movementRoutes(inventoryService: InventoryService) {
    post("/stock-movements") {
        val movement = call.receive<StockMovement>()
        if (movement.validate()) {
            inventoryService.transferStock(
                movement.itemId,
                movement.sourceLocationId,
                movement.destLocationId,
                movement.quantity
            )
            call.respond(HttpStatusCode.Created, movement)
        } else {
            call.respond(HttpStatusCode.BadRequest, "Invalid stock movement data")
        }
    }
}
