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
    implementation(libs.kotlin.stdlib)

    // hm-http
    implementation(libs.hotlibs.http)

    // Ktor
    implementation(libs.bundles.ktor.server)
    implementation(libs.ktor.server.cio)
    implementation(libs.ktor.server.swagger)

    // Jackson
    implementation(libs.bundles.jackson)
    implementation(libs.jackson.dataformat.csv)

    // Logging
    implementation(libs.hotlibs.logging)
    implementation(libs.slf4j.api)
    implementation(libs.bundles.logging.runtime)

    // Test
    testImplementation(libs.bundles.ktor.server.test)
    testImplementation(libs.bundles.junit)
}

kotlin { jvmToolchain(21) }

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    mergeServiceFiles()
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}