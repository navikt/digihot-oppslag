import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.6.10"
    id("com.diffplug.spotless") version "6.2.0"
}

group = "no.nav.hjelpemidler.oppslag"

repositories {
    mavenCentral()
}

application {
    applicationName = "digihot-oppslag"
    mainClass.set("io.ktor.server.netty.EngineMain")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

fun ktor(name: String) = "io.ktor:ktor-$name:1.6.7"

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.1") { // Snyk alert: DoS vulnerability in 2.13.0
        version {
            strictly("2.13.1")
        }
    }

    // Ktor
    implementation(ktor("server-core"))
    implementation(ktor("server-netty"))
    implementation(ktor("jackson"))
    implementation(ktor("metrics-micrometer"))

    // Logging
    implementation("io.github.microutils:kotlin-logging:2.1.21")
    runtimeOnly("ch.qos.logback:logback-classic:1.2.10")
    runtimeOnly("net.logstash.logback:logstash-logback-encoder:7.0.1")

    implementation("com.natpryce:konfig:1.6.10.0")
    implementation("io.micrometer:micrometer-registry-prometheus:1.8.2")

    // Test
    testImplementation(kotlin("test"))
    testImplementation(ktor("server-test-host"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
    testImplementation("io.mockk:mockk:1.12.2")
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

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    manifest {
        attributes["Main-Class"] = application.mainClass
    }
    from(
        configurations.runtimeClasspath.get().map {
            if (it.isDirectory) it else zipTree(it)
        }
    )
}
