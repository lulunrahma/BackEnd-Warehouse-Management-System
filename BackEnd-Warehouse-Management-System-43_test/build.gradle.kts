import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion = "2.3.12"
val kotlinVersion = "1.9.23"
val logbackVersion = "1.4.14"
val koinVersion= "3.5.3"

plugins {
    kotlin("jvm") version "2.0.21"
    id("io.ktor.plugin") version "2.3.12"
    kotlin("plugin.serialization") version "2.0.21"
    application
}

group = "com.academic"
version = "1.0.0"

application {
    mainClass.set("com.trin.erp.inventory.AppKt")
    applicationDefaultJvmArgs = listOf("--enable-native-access=ALL-UNNAMED")
}

kotlin {
    jvmToolchain (17)
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
        name = "jitpack"
    }
}

dependencies {
    // Ktor Core
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-jackson-jvm:${ktorVersion}")
    implementation("io.ktor:ktor-server-cors-jvm:${ktorVersion}")

    // Ktor Plugins
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages-jvm:$ktorVersion")

    // Dependency Injection - Koin
    implementation("io.insert-koin:koin-ktor:$koinVersion")
    implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")
    testImplementation("io.insert-koin:koin-test:$koinVersion")

    // Logging
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-server-cors:2.3.12")
    implementation("dev.hayden:khealth:2.1.1")

    // Testing (optional, good practice)
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}
