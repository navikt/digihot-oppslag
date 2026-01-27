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
    implementation("io.ktor:ktor-server-swagger")

    // Jackson
    implementation(libs.bundles.jackson)
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.16.1")

    // Logging
    implementation(libs.hotlibs.logging)
    implementation(libs.slf4j.api)
    implementation(libs.bundles.logging.runtime)

    // Test
    testImplementation(libs.bundles.ktor.server.test)
    testImplementation(libs.bundles.junit)
    testImplementation("io.mockk:mockk:1.13.8")
}

kotlin { jvmToolchain(21) }

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    mergeServiceFiles()
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}