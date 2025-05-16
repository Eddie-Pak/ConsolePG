plugins {
    kotlin("jvm") version "2.1.0"
}

group = "com.sesac.work"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation ("com.google.code.gson:gson:2.13.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}