package com.trin.erp.inventory.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertNotNull

class InventoryServiceTest {

    @Test
    fun `should add item correctly`() {
        val inventoryService = InventoryService()
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 10, locationId = 1)

        inventoryService.addItem(item)

        val result = inventoryService.getItemById(1)
        assertEquals(item, result)
    }

    @Test
    fun `should get all items`() {
        val inventoryService = InventoryService()
        val item1 = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 10, locationId = 1)
        val item2 = Item(id = 2, sku = "SKU456", name = "Item B", quantity = 15, locationId = 2)

        inventoryService.addItem(item1)
        inventoryService.addItem(item2)

        val items = inventoryService.getAllItems()
        assertEquals(2, items.size)
        assertEquals(item1, items[0])
        assertEquals(item2, items[1])
    }

    @Test
    fun `should find item by ID`() {
        val inventoryService = InventoryService()
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 10, locationId = 1)
        inventoryService.addItem(item)

        val foundItem = inventoryService.getItemById(1)
        assertNotNull(foundItem)
        assertEquals(item, foundItem)
    }

    @Test
    fun `should return null for non-existent item`() {
        val inventoryService = InventoryService()

        val foundItem = inventoryService.getItemById(999)
        assertNull(foundItem)
    }

    @Test
    fun `should update item correctly`() {
        val inventoryService = InventoryService()
        val originalItem = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 10, locationId = 1)
        inventoryService.addItem(originalItem)

        val updatedItem = Item(id = 1, sku = "SKU123", name = "Updated Item A", quantity = 20, locationId = 2)
        inventoryService.updateItem(updatedItem)

        val itemAfterUpdate = inventoryService.getItemById(1)
        assertEquals("Updated Item A", itemAfterUpdate?.name)
        assertEquals(20, itemAfterUpdate?.quantity)
    }

    @Test
    fun `should remove item correctly`() {
        val inventoryService = InventoryService()
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 10, locationId = 1)
        inventoryService.addItem(item)

        inventoryService.removeItem(1)

        val foundItem = inventoryService.getItemById(1)
        assertNull(foundItem)
    }

    @Test
    fun `should return empty list when no items exist`() {
        val inventoryService = InventoryService()

        val items = inventoryService.getAllItems()
        assertEquals(0, items.size)
    }

    @Test
    fun `should receive stock correctly`() {
        val inventoryService = InventoryService()
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 10, locationId = 1)
        inventoryService.addItem(item)

        inventoryService.receiveStock(1, 15)

        val updatedItem = inventoryService.getItemById(1)
        assertEquals(25, updatedItem?.quantity) // 10 + 15 = 25
    }

    @Test
    fun `should get stock report for specific item and warehouse`() {
        val inventoryService = InventoryService()
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 10, locationId = 1)
        inventoryService.addItem(item)

        val stockReport = inventoryService.getStockReport(1, 1)
        assertEquals(10, stockReport)
    }
}
