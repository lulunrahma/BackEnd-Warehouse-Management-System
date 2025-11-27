package com.trin.erp.inventory.utils

object Validator {
    // Validasi untuk stok item
    fun validateStockQuantity(quantity: Int): Boolean {
        return quantity > 0
    }

    // Validasi format SKU
    fun validateSku(sku: String): Boolean {
        return sku.isNotEmpty() && sku.matches("^[A-Z0-9]+$".toRegex())  // Contoh validasi SKU dengan huruf dan angka
    }
}
