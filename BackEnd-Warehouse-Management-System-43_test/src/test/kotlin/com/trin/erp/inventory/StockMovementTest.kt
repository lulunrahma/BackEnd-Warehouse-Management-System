package com.trin.erp.inventory.domain

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class StockMovementTest {

    @Test
    fun `should validate movement with positive quantity and valid type`() {
        val movement = StockMovement(
            id = 1,
            itemId = 1,
            type = MovementType.IN,
            quantity = 10,
            date = "2025-01-01",
            sourceLocationId = 1,
            destLocationId = 2
        )
        
        assertTrue(movement.validate())
    }
    
    @Test
    fun `should not validate movement with zero quantity`() {
        val movement = StockMovement(
            id = 1,
            itemId = 1,
            type = MovementType.IN,
            quantity = 0,
            date = "2025-01-01",
            sourceLocationId = 1,
            destLocationId = 2
        )
        
        assertFalse(movement.validate())
    }
    
    @Test
    fun `should not validate movement with negative quantity`() {
        val movement = StockMovement(
            id = 1,
            itemId = 1,
            type = MovementType.IN,
            quantity = -5,
            date = "2025-01-01",
            sourceLocationId = 1,
            destLocationId = 2
        )
        
        assertFalse(movement.validate())
    }
    
    @Test
    fun `should validate IN movement type`() {
        val movement = StockMovement(
            id = 1,
            itemId = 1,
            type = MovementType.IN,
            quantity = 10,
            date = "2025-01-01",
            sourceLocationId = 1,
            destLocationId = 2
        )
        
        assertTrue(movement.validate())
    }
    
    @Test
    fun `should validate OUT movement type`() {
        val movement = StockMovement(
            id = 1,
            itemId = 1,
            type = MovementType.OUT,
            quantity = 10,
            date = "2025-01-01",
            sourceLocationId = 1,
            destLocationId = 2
        )
        
        assertTrue(movement.validate())
    }
    
    @Test
    fun `should validate TRANSFER movement type`() {
        val movement = StockMovement(
            id = 1,
            itemId = 1,
            type = MovementType.TRANSFER,
            quantity = 10,
            date = "2025-01-01",
            sourceLocationId = 1,
            destLocationId = 2
        )
        
        assertTrue(movement.validate())
    }
}