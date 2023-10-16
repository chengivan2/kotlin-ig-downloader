// build.gradle.kts

plugins {
    kotlin("jvm") version "1.5.31"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jsoup:jsoup:1.14.3")
    implementation("khttp:khttp:1.0.0")
}