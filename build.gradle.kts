plugins {
    kotlin("jvm") version "2.0.0"
    id("app.cash.sqldelight") version "2.0.2"
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
    implementation("app.cash.sqldelight:jdbc-driver:2.0.2")
    implementation("com.zaxxer:HikariCP:5.1.0")
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