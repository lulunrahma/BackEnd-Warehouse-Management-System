package com.trin.erp.inventory.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class StockServiceTest {

    @Test
    fun `should add stock correctly`() {
        val inventoryService = InventoryService()
        val stockService = StockService(inventoryService)
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 10, locationId = 1)
        inventoryService.addItem(item)
        
        stockService.addStock(1, 5)
        
        val updatedItem = inventoryService.getItemById(1)
        assertEquals(15, updatedItem?.quantity) // 10 + 5 = 15
    }
    
    @Test
    fun `should fail to add stock when item not found`() {
        val inventoryService = InventoryService()
        val stockService = StockService(inventoryService)
        
        assertFailsWith<IllegalArgumentException> {
            stockService.addStock(999, 5)
        }
    }
    
    @Test
    fun `should transfer stock successfully`() {
        val inventoryService = InventoryService()
        val stockService = StockService(inventoryService)
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 20, locationId = 1)
        inventoryService.addItem(item)
        
        stockService.transferStock(1, 1, 2, 5)
        
        val updatedItem = inventoryService.getItemById(1)
        assertEquals(15, updatedItem?.quantity) // 20 - 5 = 15
    }
    
    @Test
    fun `should fail to transfer stock when item not found`() {
        val inventoryService = InventoryService()
        val stockService = StockService(inventoryService)
        
        assertFailsWith<IllegalArgumentException> {
            stockService.transferStock(999, 1, 2, 5)
        }
    }
    
    @Test
    fun `should fail to transfer stock when insufficient quantity`() {
        val inventoryService = InventoryService()
        val stockService = StockService(inventoryService)
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 3, locationId = 1)
        inventoryService.addItem(item)
        
        assertFailsWith<IllegalArgumentException> {
            stockService.transferStock(1, 1, 2, 5)
        }
    }
    
    @Test
    fun `should register movement correctly`() {
        val inventoryService = InventoryService()
        val stockService = StockService(inventoryService)
        
        stockService.registerMovement(1, "IN", 10, "2025-01-01", 1, 2)
        
        val movements = stockService.getAllMovements()
        assertEquals(1, movements.size)
        assertEquals(1, movements[0].itemId)
        assertEquals(10, movements[0].quantity)
    }
    
    @Test
    fun `should get movement by ID`() {
        val inventoryService = InventoryService()
        val stockService = StockService(inventoryService)
        
        stockService.registerMovement(1, "IN", 10, "2025-01-01", 1, 2)
        
        val movement = stockService.getMovementById(1)
        assertNotNull(movement)
        assertEquals(1, movement?.itemId)
    }
    
    @Test
    fun `should get all movements`() {
        val inventoryService = InventoryService()
        val stockService = StockService(inventoryService)
        
        stockService.registerMovement(1, "IN", 10, "2025-01-01", 1, 2)
        stockService.registerMovement(2, "OUT", 5, "2025-01-02", 2, 3)
        
        val movements = stockService.getAllMovements()
        assertEquals(2, movements.size)
    }
    
    @Test
    fun `should register movement object correctly`() {
        val inventoryService = InventoryService()
        val stockService = StockService(inventoryService)
        val movement = StockMovement(
            id = 1,
            itemId = 1,
            type = MovementType.IN,
            quantity = 10,
            date = "2025-01-01",
            sourceLocationId = 1,
            destLocationId = 2
        )
        
        stockService.registerMovementObject(movement)
        
        val movements = stockService.getAllMovements()
        assertEquals(1, movements.size)
        assertEquals(movement, movements[0])
    }
}