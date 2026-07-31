group = "no.nav.hjelpemidler.oppslag"
version = "1.0-SNAPSHOT"

plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    // hotlibs
    implementation(platform(libs.hotlibs.platform))
    implementation(libs.hotlibs.core)
    implementation(libs.hotlibs.http)
    implementation(libs.hotlibs.logging)
    implementation(libs.hotlibs.serialization)
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
                implementation(libs.hotlibs.test)
            }
        }
    }
}
