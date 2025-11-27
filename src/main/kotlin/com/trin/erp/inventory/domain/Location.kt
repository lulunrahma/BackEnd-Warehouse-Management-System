package com.trin.erp.inventory.domain

data class Location(
    val id: Int,
    val warehouseId: Int,
    val rack: String,  // Rak dalam gudang
    val bin: String    // Bin dalam rak
)
