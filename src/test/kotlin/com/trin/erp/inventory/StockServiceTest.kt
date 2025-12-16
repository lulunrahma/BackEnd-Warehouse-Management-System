package com.trin.erp.inventory

import com.trin.erp.inventory.domain.MovementType
import com.trin.erp.inventory.domain.StockService
import com.trin.erp.inventory.domain.StockMovement
import org.junit.Test
import kotlin.test.assertEquals

class StockServiceTest {

    private val stockService = StockService()

    @Test
    fun `should register stock movement successfully`() {
        val movement = StockMovement(
            1, 5, MovementType.IN, 10,
            date = "19-05-2020",
            sourceLocationId = 5,
            destLocationId = 7
        )
        stockService.registerMovement(movement)

        val movements = stockService.getAllMovements()
        assertEquals(1, movements.size)  // Verifikasi bahwa pergerakan stok tercatat
    }

    @Test
    fun `should get all stock movements successfully`() {
        val movement1 = StockMovement(
            1, 5, MovementType.IN, 10,
            date = "12-09-2025",
            sourceLocationId = 1,
            destLocationId = 2
        )
        val movement2 = StockMovement(
            2, 6, MovementType.OUT, 5,
            date = "20-09-2023",
            sourceLocationId = 4,
            destLocationId = 7
        )
        stockService.registerMovement(movement1)
        stockService.registerMovement(movement2)

        val movements = stockService.getAllMovements()
        assertEquals(2, movements.size)  // Verifikasi bahwa dua pergerakan stok dikembalikan
    }

    @Test
    fun `should get movement by ID`() {
        val movement = StockMovement(
            1, 5, MovementType.IN, 10,
            date = "12-05-2025",
            sourceLocationId = 1,
            destLocationId = 2
        )
        stockService.registerMovement(movement)

        val retrievedMovement = stockService.getMovementById(1)
        assertEquals(movement, retrievedMovement)  // Verifikasi bahwa pergerakan stok yang tepat dikembalikan
    }
}
