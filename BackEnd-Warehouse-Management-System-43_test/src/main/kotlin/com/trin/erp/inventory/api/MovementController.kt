package com.trin.erp.inventory.api

import com.trin.erp.inventory.domain.InventoryService
import com.trin.erp.inventory.domain.StockMovement
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

class MovementController(private val inventoryService: InventoryService) {

    fun Route.registerMovementRoutes() {
        // Endpoint untuk memindahkan stok (IN, OUT, TRANSFER)
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
}
