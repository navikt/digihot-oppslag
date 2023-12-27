import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"
    id("io.ktor.plugin") version "2.3.7"
    id("com.diffplug.spotless") version "6.23.3"
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
    implementation("io.micrometer:micrometer-registry-prometheus:1.12.1")

    implementation("io.ktor:ktor-serialization-jackson")
    implementation("io.ktor:ktor-server-call-logging")
    implementation("io.ktor:ktor-server-cio")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("io.ktor:ktor-server-metrics-micrometer")
    implementation("io.ktor:ktor-server-swagger")

    // Jackson
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.16.1")

    // Logging
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    runtimeOnly("ch.qos.logback:logback-classic:1.4.14")
    runtimeOnly("net.logstash.logback:logstash-logback-encoder:7.4")

    // Test
    testImplementation(kotlin("test"))
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testImplementation("io.mockk:mockk:1.13.8")
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

val javaVersion = JavaLanguageVersion.of(21)
java { toolchain { languageVersion.set(javaVersion) } }
kotlin { jvmToolchain { languageVersion.set(javaVersion) } }

tasks.withType<KotlinCompile> {
    dependsOn("spotlessApply")
    dependsOn("spotlessCheck")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
