import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version = Ktor.version
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version Kotlin.version
    id(Spotless.spotless) version Spotless.version
    id(Shadow.shadow) version Shadow.version
}

group = "no.nav.hjelpemidler.oppslag"

buildscript {
    repositories {
        jcenter()
    }
}

apply {
    plugin(Spotless.spotless)
}

repositories {
    mavenCentral()
    jcenter()
}

application {
    applicationName = "digihot-oppslag"
    mainClassName = "io.ktor.server.netty.EngineMain"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation("ch.qos.logback:logback-classic:1.2.7")
    implementation("net.logstash.logback:logstash-logback-encoder:7.0.1")
    implementation(Ktor.serverNetty)
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation(Konfig.konfig)
    implementation(Kotlin.Logging.kotlinLogging)
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.ktor:ktor-jackson:$ktor_version")

    // Snyk alert: DoS vulnerability in 2.13.0
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.1") {
        version {
            strictly("2.13.1")
        }
    }

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
    testImplementation(Ktor.ktorTest)
    testImplementation(Mockk.mockk)
}

spotless {
    kotlin {
        ktlint(Ktlint.version)
    }
    kotlinGradle {
        target("*.gradle.kts", "buildSrc/*.gradle.kts")
        ktlint(Ktlint.version)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.freeCompilerArgs = listOf()
    kotlinOptions.jvmTarget = "11"
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        showExceptions = true
        showStackTraces = true
        showStandardStreams = true
        outputs.upToDateWhen { false }
        exceptionFormat = TestExceptionFormat.FULL
        events = setOf(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
    }
}

tasks.withType<Wrapper> {
    gradleVersion = "7.2"
}

tasks.named("shadowJar") {
    dependsOn("test")
}

tasks.named("jar") {
    dependsOn("test")
}

tasks.named("compileKotlin") {
    dependsOn("spotlessApply")
    dependsOn("spotlessCheck")
}
