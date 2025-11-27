package com.trin.erp.inventory.domain

import com.trin.erp.inventory.infra.InMemoryRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class InventoryService {
    private val repository = InMemoryRepository()
    private val warehouses = mutableListOf<Warehouse>()
    private val locations = mutableListOf<Location>()
    private val movements = mutableListOf<StockMovement>()

    // Menambahkan item baru
    fun addItem(item: Item) {
        repository.addItem(item)
    }

    // Mengambil daftar semua item
    fun getAllItems(): List<Item> {
        return repository.getAllItems()
    }

    // Mengupdate item
    fun updateItem(updatedItem: Item) {
        repository.updateItem(updatedItem)
    }

    // Menghapus item
    fun removeItem(id: Int) {
        repository.removeItem(id)
    }

    // Mengambil item berdasarkan id
    fun getItemById(id: Int): Item? {
        return repository.getItemById(id)
    }

    // Menambah stok untuk item tertentu
    fun receiveStock(itemId: Int, quantity: Int) {
        val item = getItemById(itemId)
        item?.increaseStock(quantity)
    }

    // Transfer stok antar lokasi
    fun transferStock(itemId: Int, sourceLocationId: Int, destLocationId: Int, quantity: Int) {
        val item = getItemById(itemId) ?: throw IllegalArgumentException("Item not found")
        if (item.quantity >= quantity) {
            item.decreaseStock(quantity)
            val movement = StockMovement(
                id = movements.size + 1,
                itemId = itemId,
                type = MovementType.TRANSFER,
                quantity = quantity,
                date = getCurrentDate(), // Gunakan tanggal saat ini
                sourceLocationId = sourceLocationId,
                destLocationId = destLocationId
            )
            movements.add(movement)
        } else {
            throw IllegalArgumentException("Insufficient stock to transfer")
        }
    }

    // Melihat laporan stok item di lokasi tertentu
    fun getStockReport(itemId: Int, warehouseId: Int): Int {
        val item = repository.getItemById(itemId)
        return if (item != null && item.locationId == warehouseId) item.quantity else 0
    }

    private fun getCurrentDate(): String {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }
}
