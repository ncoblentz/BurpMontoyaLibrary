plugins {
    id("java-library")
    id("maven-publish")

    // Provides the shadowJar task in Gradle
    // https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow
    id("com.github.johnrengelman.shadow") version "8.1.1"

    id("com.github.ben-manes.versions") version "0.51.0" //Gradle -> Help -> dependencyUpdates

    // Provides Kotlin Language Support
    // https://plugins.gradle.org/plugin/org.jetbrains.kotlin.jvm
    kotlin("jvm") version "2.0.20"
}

group = "com.nickcoblentz.montoya"
version = "0.1.23"

repositories {
    mavenLocal()
    mavenCentral()
    maven(url="https://jitpack.io") {
        content {
            includeGroup("com.github.milchreis")
            includeGroup("com.github.ncoblentz")
        }
    }
}

dependencies {
    //testImplementation(platform("org.junit:junit-bom:5.10.0"))
    //testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("net.portswigger.burp.extensions:montoya-api:2024.7")
    //implementation("org.httprpc:sierra:2.2")
    //implementation("com.google.code.gson:gson:2.11.0")

    //https://github.com/Milchreis/UiBooster/releases
    implementation("com.github.milchreis:uibooster:1.21.1")


    //implementation("org.json:json:+")
    //implementation(kotlin("stdlib-jdk8"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = group.toString()
            artifactId = rootProject.name
            version = version.toString()

            from(components["java"])
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}