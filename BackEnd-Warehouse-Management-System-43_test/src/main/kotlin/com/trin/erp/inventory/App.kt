package com.trin.erp.inventory

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import com.trin.erp.inventory.api.inventoryRoutes
import com.trin.erp.inventory.api.movementRoutes
import com.trin.erp.inventory.domain.InventoryService
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import org.koin.ktor.ext.get

val appModule: Module = module {
    single { InventoryService() }  // Mendefinisikan InventoryService sebagai singleton
}

fun Application.module() {
    // Inisialisasi Koin
    startKoin {
        modules(appModule)  // Menyuntikkan modul Koin
    }

    install(ContentNegotiation) {
        jackson { }
    }

    val inventoryService = get<InventoryService>()  // Mengambil instance InventoryService dari Koin

    routing {
        inventoryRoutes(inventoryService)  // Menyuntikkan inventoryService ke dalam routes
        movementRoutes(inventoryService)   // Menyuntikkan inventoryService ke dalam routes
    }
}

fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}
