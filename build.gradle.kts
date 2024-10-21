plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("jupyter.api") version "0.12.0-322"
    id("org.jetbrains.compose") version "1.7.0"
//    id("org.jetbrains.kotlin.plugin.compose") version "1.9.23"
}

group = "com.beetstra.turtlegraphics"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

compose.desktop {
    application {
        mainClass = "com.beetstra.turtlegraphics.compose.MainKt"
    }
}

dependencies {
    implementation(compose.desktop.macos_arm64)
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
