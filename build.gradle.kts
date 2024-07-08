plugins {
    id("java-library")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("com.github.ben-manes.versions") version "0.51.0" //Gradle -> Help -> dependencyUpdates
    id("org.owasp.dependencycheck") version "10.0.1" // owasp dependency-check -> dependencyCheckAnalyze
}

group = "com.nickcoblentz.montoya"
version = "0.1.10"

repositories {
    mavenLocal()
    mavenCentral()
    maven(url="https://jitpack.io")
}

dependencies {
    //testImplementation(platform("org.junit:junit-bom:5.10.0"))
    //testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("net.portswigger.burp.extensions:montoya-api:2023.12.1")
    //implementation("org.httprpc:sierra:2.2")
    //implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.github.milchreis:uibooster:1.21.1")


    //implementation("org.json:json:+")
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