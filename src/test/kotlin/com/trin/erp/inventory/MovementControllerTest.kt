package com.trin.erp.inventory.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class InventoryMovementTest {

    @Test
    fun `should transfer stock correctly`() {
        val inventoryService = InventoryService()
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 20, locationId = 1)
        inventoryService.addItem(item)

        // Perform the transfer
        inventoryService.transferStock(1, 1, 2, 10)

        // Verify the item quantity has decreased in the source location
        val updatedItem = inventoryService.getItemById(1)
        assertEquals(10, updatedItem?.quantity) // 20 - 10 = 10
    }

    @Test
    fun `should fail to transfer stock when insufficient quantity`() {
        val inventoryService = InventoryService()
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 5, locationId = 1)
        inventoryService.addItem(item)

        // Attempt to transfer more than available
        assertFailsWith<IllegalArgumentException> {
            inventoryService.transferStock(1, 1, 2, 10)
        }
    }

    @Test
    fun `should fail to transfer stock when item not found`() {
        val inventoryService = InventoryService()

        // Attempt to transfer non-existent item
        assertFailsWith<IllegalArgumentException> {
            inventoryService.transferStock(999, 1, 2, 10)
        }
    }

    @Test
    fun `should handle zero quantity transfer`() {
        val inventoryService = InventoryService()
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 20, locationId = 1)
        inventoryService.addItem(item)

        // Attempt to transfer 0 quantity
        assertFailsWith<IllegalArgumentException> {
            inventoryService.transferStock(1, 1, 2, 0)
        }
    }

    @Test
    fun `should handle negative quantity transfer`() {
        val inventoryService = InventoryService()
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 20, locationId = 1)
        inventoryService.addItem(item)

        // Attempt to transfer negative quantity
        assertFailsWith<IllegalArgumentException> {
            inventoryService.transferStock(1, 1, 2, -5)
        }
    }
}
