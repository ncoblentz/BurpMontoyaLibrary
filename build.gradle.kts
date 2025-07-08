plugins {
    id("java-library")

// Provides the shadowJar task in Gradle
    // https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow
    id("com.github.johnrengelman.shadow") version "8.1.1"

    id("com.github.ben-manes.versions") version "0.51.0" //Gradle -> Help -> dependencyUpdates

    // Provides Kotlin Language Support
    // https://plugins.gradle.org/plugin/org.jetbrains.kotlin.jvm
    kotlin("jvm") version "2.2.0"
}

group = "com.nickcoblentz.montoya"
version = "0.2.0"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("net.portswigger.burp.extensions:montoya-api:2025.6")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}