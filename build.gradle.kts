plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("asm:asm:3.3.1")
    implementation("org.ow2.asm:asm-tree:9.5")

}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}