group = "no.nav.hjelpemidler.oppslag"
version = "1.0-SNAPSHOT"

plugins {
    alias(libs.plugins.kotlin.jvm)

    `java-library`
    `maven-publish`
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
    withSourcesJar()
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

publishing {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/navikt/digihot-oppslag")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

group = "no.nav.hjelpemidler.oppslag"
version = System.getenv("VERSION_TAG") ?: "local"
