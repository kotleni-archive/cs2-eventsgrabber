import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.21"
    id("maven-publish")
    application
}

group = "kotleni.cs2eventsgrabber"
version = "1.0-alpha"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    testImplementation(kotlin("test"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "kotleni.cs2eventsgrabber"
            artifactId = "kotleni.cs2eventsgrabber"
            version = "1.0-alpha"

            afterEvaluate {
                from(components["java"])
            }
        }
    }
}