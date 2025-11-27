package com.trin.erp.inventory.domain

class InventoryService {
    private val items = mutableListOf<Item>()
    private val warehouses = mutableListOf<Warehouse>()
    private val locations = mutableListOf<Location>()
    private val movements = mutableListOf<StockMovement>()

    // Menambahkan item baru
    fun addItem(item: Item) {
        items.add(item)
    }

    // Mengambil daftar semua item
    fun getAllItems(): List<Item> {
        return items
    }

    // Mengupdate item
    fun updateItem(updatedItem: Item) {
        val itemIndex = items.indexOfFirst { it.id == updatedItem.id }
        if (itemIndex >= 0) {
            items[itemIndex] = updatedItem
        }
    }

    // Menghapus item
    fun removeItem(id: Int) {
        items.removeIf { it.id == id }
    }

    // Mengambil item berdasarkan id
    fun getItemById(id: Int): Item? {
        return items.find { it.id == id }
    }

    // Menambah stok untuk item tertentu
    fun receiveStock(itemId: Int, quantity: Int) {
        val item = getItemById(itemId)
        item?.increaseStock(quantity)
    }

    // Transfer stok antar gudang
    fun transferStock(itemId: Int, fromWarehouseId: Int, toWarehouseId: Int, quantity: Int) {
        val item = getItemById(itemId) ?: throw IllegalArgumentException("Item not found")
        if (item.quantity >= quantity) {
            item.decreaseStock(quantity)
            val movement = StockMovement(
                id = movements.size + 1,
                itemId = itemId,
                type = MovementType.TRANSFER,
                quantity = quantity,
                date = "2025-11-26", // Gunakan tanggal saat ini
                sourceLocationId = fromWarehouseId,
                destLocationId = toWarehouseId
            )
            movements.add(movement)
        } else {
            throw IllegalArgumentException("Insufficient stock to transfer")
        }
    }

    // Melihat laporan stok item di lokasi tertentu
    fun getStockReport(itemId: Int, warehouseId: Int): Int {
        return items.filter { it.id == itemId && it.locationId == warehouseId }.sumOf { it.quantity }
    }
}
