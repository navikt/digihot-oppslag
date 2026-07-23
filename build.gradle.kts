group = "no.nav.hjelpemidler"
version = "1.0-SNAPSHOT"

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
}

application {
    applicationName = "digihot-oppslag"
    mainClass.set("io.ktor.server.cio.EngineMain")
}

dependencies {
    // hotlibs
    implementation(platform(libs.hotlibs.platform))
    implementation(libs.hotlibs.core)
    implementation(libs.hotlibs.http)
    implementation(libs.hotlibs.logging)
    implementation(libs.hotlibs.serialization)

    // Ktor
    implementation(libs.bundles.ktor.server)
    implementation(libs.ktor.server.cio)
    implementation(libs.ktor.server.swagger)

    // Jackson
    implementation(libs.jackson.dataformat.csv)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

testing {
    suites {
        @Suppress("UnstableApiUsage")
        val test = named<JvmTestSuite>("test") {
            useJUnitJupiter(libs.versions.junit)
            dependencies {
                implementation(libs.ktor.server.test.host)
                implementation(libs.hotlibs.test)
            }
        }
    }
}

tasks.shadowJar {
    mergeServiceFiles()
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}
