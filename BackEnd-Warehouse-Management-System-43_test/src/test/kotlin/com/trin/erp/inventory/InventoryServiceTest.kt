package com.trin.erp.inventory

import com.trin.erp.inventory.domain.InventoryService
import com.trin.erp.inventory.domain.Item
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class InventoryServiceTest {

    private val inventoryService = InventoryService()

    @Test
    fun `should add and retrieve item successfully`() {
        val item = Item(
            1, "Item A", "Book",
            quantity = 12,
            locationId = 5
        )
        inventoryService.addItem(item)
        val retrievedItem = inventoryService.getItemById(1)

        assertEquals(item, retrievedItem)  // Verifikasi bahwa item yang ditambahkan berhasil diambil
    }

    @Test
    fun `should update item successfully`() {
        val item = Item(
            1, "Item A", "Book",
            quantity = 12,
            locationId = 5
        )
        inventoryService.addItem(item)
        val updatedItem = Item(
            1, "Item A Updated", "Laptop",
            quantity = 12,
            locationId = 5
        )
        inventoryService.updateItem(updatedItem)

        val retrievedItem = inventoryService.getItemById(1)
        assertEquals(updatedItem, retrievedItem)  // Verifikasi bahwa item berhasil diperbarui
    }

    @Test
    fun `should remove item successfully`() {
        val item = Item(
            1, "Item A", "Book",
            quantity = 12,
            locationId = 5
        )
        inventoryService.addItem(item)
        inventoryService.removeItem(1)

        val retrievedItem = inventoryService.getItemById(1)
        assertNull(retrievedItem)  // Verifikasi bahwa item berhasil dihapus
    }

    @Test
    fun `should get all items successfully`() {
        val item1 = Item(
            1, "Item A", "Laptop",
            quantity = 12,
            locationId = 5
        )
        val item2 = Item(
            2, "Item B", "Tablet",
            quantity = 6,
            locationId = 19
        )
        inventoryService.addItem(item1)
        inventoryService.addItem(item2)

        val items = inventoryService.getAllItems()
        assertEquals(2, items.size)  // Verifikasi bahwa dua item dikembalikan
    }
}
