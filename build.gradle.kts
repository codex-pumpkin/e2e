plugins {
    kotlin("jvm") version "2.1.10"
}

group = "com.codex.e2e"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("com.microsoft.playwright:playwright:1.41.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}