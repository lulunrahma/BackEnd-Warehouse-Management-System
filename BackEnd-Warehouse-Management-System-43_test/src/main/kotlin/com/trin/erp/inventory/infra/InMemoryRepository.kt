package com.trin.erp.inventory.infra

import com.trin.erp.inventory.domain.Item
import com.trin.erp.inventory.domain.Warehouse
import com.trin.erp.inventory.domain.Location

class InMemoryRepository {
    private val items = mutableListOf<Item>()
    private val warehouses = mutableListOf<Warehouse>()
    private val locations = mutableListOf<Location>()

    fun addItem(item: Item) {
        items.add(item)
    }

    fun getItemById(id: Int): Item? {
        return items.find { it.id == id }
    }

    fun getAllItems(): List<Item> {
        return items
    }

    // Add more repository methods for warehouses, locations, etc.
}
