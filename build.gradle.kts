import kotlin.collections.emptyList;

plugins {
    kotlin("jvm") version "1.3.71"
    id("application")
    id("com.bmuschko.docker-java-application") version "6.4.0"
}

group = "space.jrosn.mafiator"
version = "1.0-SNAPSHOT"

application {
    mainClassName = "ApplicationKt"
}

docker {
    registryCredentials {
        username.set(System.getenv("GITHUB_USERNAME"))
        password.set(System.getenv("GITHUB_TOKEN"))
        url.set("https://docker.pkg.github.com")
    }
    javaApplication {
        maintainer.set("Roman Sosnovsky 'rvsosnovsky@gmail.com'")
        ports.set(emptyList())
    }
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