plugins {
    kotlin("jvm") version "1.3.71"
    id("application")
}

group = "space.jrosn.mafiator"
version = "1.0-SNAPSHOT"

application {
    mainClassName = "ApplicationKt"
}

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.3.7")

    // Discord
    implementation("com.discord4j:discord4j-core:3.1.0.RC2")

    // Logging
    implementation("io.github.microutils:kotlin-logging:1.7.9")
    implementation("ch.qos.logback:logback-classic:1.3.0-alpha5")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}