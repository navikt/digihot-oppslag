import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.9.0"
    id("com.diffplug.spotless") version "6.13.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "no.nav.hjelpemidler.oppslag"

repositories {
    mavenCentral()
}

application {
    applicationName = "digihot-oppslag"
    mainClass.set("io.ktor.server.cio.EngineMain")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.micrometer:micrometer-registry-prometheus:1.10.5")

    // Ktor
    fun ktor(name: String) = "io.ktor:ktor-$name:2.3.3"
    implementation(ktor("server-core"))
    implementation(ktor("server-cio"))
    implementation(ktor("server-content-negotiation"))
    implementation(ktor("server-call-logging"))
    implementation(ktor("server-metrics-micrometer"))
    implementation(ktor("server-swagger"))
    implementation(ktor("serialization-jackson"))

    // Jackson
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.14.2")

    // Logging
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    implementation(ktor("server-cors-jvm"))
    runtimeOnly("ch.qos.logback:logback-classic:1.4.5")
    runtimeOnly("net.logstash.logback:logstash-logback-encoder:7.2")

    // Test
    testImplementation(kotlin("test"))
    testImplementation(ktor("server-test-host"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("io.mockk:mockk:1.13.3")
}

spotless {
    kotlin {
        ktlint()
        targetExclude("**/generated/**")
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
}

tasks.withType<KotlinCompile> {
    dependsOn("spotlessApply")
    dependsOn("spotlessCheck")

    kotlinOptions.jvmTarget = "17"
}

tasks.withType<Test> {
    useJUnitPlatform()
}
