package com.trin.erp.inventory.domain

data class StockMovement(
    val id: Int,
    val itemId: Int,
    val type: MovementType,
    val quantity: Int,
    val date: String,
    val sourceLocationId: Int,
    val destLocationId: Int
) {
    // Validasi untuk pergerakan stok
    fun validate(): Boolean {
        return quantity > 0 && (type == MovementType.IN || type == MovementType.OUT || type == MovementType.TRANSFER)
    }
}
