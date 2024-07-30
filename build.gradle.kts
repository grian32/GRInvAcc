plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    id("app.cash.sqldelight") version "2.0.2"
    // required for flyway to work with psql ref: https://github.com/flyway/flyway/issues/3889#issuecomment-2131050408
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "me.grian"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.ktor:ktor-server-netty:3.0.0-beta-2")
    implementation("io.ktor:ktor-server-content-negotiation:3.0.0-beta-2")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.0-beta-2")
    implementation("app.cash.sqldelight:jdbc-driver:2.0.2")
    implementation("org.flywaydb:flyway-core:10.16.0")
    implementation("com.h2database:h2:2.2.224")
    implementation("org.flywaydb:flyway-database-postgresql:10.16.0")
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("me.grian")
            dialect("app.cash.sqldelight:postgresql-dialect:2.0.2")
        }
    }
}