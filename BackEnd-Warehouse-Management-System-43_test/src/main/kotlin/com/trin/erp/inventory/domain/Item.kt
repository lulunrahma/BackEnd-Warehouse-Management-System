package com.trin.erp.inventory.domain

data class Item(
    var id: Int,
    val sku: String,
    val name: String,
    var quantity: Int, // Kuantitas barang
    val locationId: Int
) {
    // Method untuk mengecek ketersediaan stok
    fun isAvailable(): Boolean = quantity > 0

    // Method untuk menambah stok
    fun increaseStock(amount: Int) {
        if (amount > 0) {
            quantity += amount
        } else {
            throw IllegalArgumentException("Amount must be greater than 0")
        }
    }

    // Method untuk mengurangi stok
    fun decreaseStock(amount: Int) {
        if (amount > 0 && quantity >= amount) {
            quantity -= amount
        } else {
            throw IllegalArgumentException("Insufficient stock or invalid amount")
        }
    }
}
