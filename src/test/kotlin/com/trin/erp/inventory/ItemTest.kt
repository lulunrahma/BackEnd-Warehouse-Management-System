package com.trin.erp.inventory.domain

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ItemTest {

    @Test
    fun `item should be available when quantity is greater than 0`() {
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 5, locationId = 1)
        
        assertTrue(item.isAvailable())
    }

    @Test
    fun `item should not be available when quantity is 0`() {
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 0, locationId = 1)
        
        assertFalse(item.isAvailable())
    }

    @Test
    fun `item should not be available when quantity is negative`() {
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = -5, locationId = 1)
        
        assertFalse(item.isAvailable())
    }
    
    @Test
    fun `should increase stock correctly`() {
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 10, locationId = 1)
        
        item.increaseStock(5)
        
        assertEquals(15, item.quantity)
    }
    
    @Test
    fun `should fail to increase stock with negative amount`() {
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 10, locationId = 1)
        
        assertFailsWith<IllegalArgumentException> {
            item.increaseStock(-5)
        }
    }
    
    @Test
    fun `should fail to increase stock with zero amount`() {
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 10, locationId = 1)
        
        assertFailsWith<IllegalArgumentException> {
            item.increaseStock(0)
        }
    }
    
    @Test
    fun `should decrease stock correctly`() {
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 10, locationId = 1)
        
        item.decreaseStock(5)
        
        assertEquals(5, item.quantity)
    }
    
    @Test
    fun `should fail to decrease stock when amount is greater than available`() {
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 5, locationId = 1)
        
        assertFailsWith<IllegalArgumentException> {
            item.decreaseStock(10)
        }
    }
    
    @Test
    fun `should fail to decrease stock with negative amount`() {
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 10, locationId = 1)
        
        assertFailsWith<IllegalArgumentException> {
            item.decreaseStock(-5)
        }
    }
    
    @Test
    fun `should fail to decrease stock with zero amount`() {
        val item = Item(id = 1, sku = "SKU123", name = "Item A", quantity = 10, locationId = 1)
        
        assertFailsWith<IllegalArgumentException> {
            item.decreaseStock(0)
        }
    }
}