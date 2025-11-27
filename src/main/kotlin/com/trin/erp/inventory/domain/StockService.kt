package com.trin.erp.inventory.domain

class StockService(private val inventoryService: InventoryService) {

    private val movements = mutableListOf<StockMovement>()
    // Menambah stok barang masuk
    fun addStock(itemId: Int, quantity: Int) {
        val item = inventoryService.getItemById(itemId)
        item?.increaseStock(quantity) ?: throw IllegalArgumentException("Item not found")
    }

    // Memindahkan stok antara dua lokasi
    fun transferStock(itemId: Int, fromLocationId: Int, toLocationId: Int, quantity: Int) {
        val item = inventoryService.getItemById(itemId)
        if (item != null) {
            if (item.isAvailable() && item.quantity >= quantity) {
                inventoryService.transferStock(itemId, fromLocationId, toLocationId, quantity)
            } else {
                throw IllegalArgumentException("Insufficient stock to transfer")
            }
        } else {
            throw IllegalArgumentException("Item not found")
        }
    }
    fun registerMovement(itemId: Int, type: String, quantity: Int, date: String, sourceLocationId: Int, destLocationId: Int) {
        // Membuat objek StockMovement dengan data yang diberikan
        val movement = StockMovement(
            id = movements.size + 1,  // Menggunakan ukuran list sebagai ID
            itemId = itemId,
            type = MovementType.valueOf(type),
            quantity = quantity,
            date = date,
            sourceLocationId = sourceLocationId,
            destLocationId = destLocationId
        )
        // Menambahkan objek StockMovement ke list movements
        movements.add(movement)

        // Menampilkan log pergerakan stok
        println("Movement registered: $movement")
    }

    // Mendapatkan semua pergerakan stok
    fun getAllMovements(): List<StockMovement> {
        return movements
    }

    // Mendapatkan pergerakan stok berdasarkan ID
    fun getMovementById(id: Int): StockMovement? {
        return movements.find { it.id == id }
    }

    fun registerMovement(itemId: StockMovement) {}
}
