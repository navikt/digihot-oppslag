object Konfig {
    const val konfig = "com.natpryce:konfig:1.6.10.0"
}

object Kotlin {
    const val version = "1.5.31"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
    const val testJUnit5 = "org.jetbrains.kotlin:kotlin-test-junit5:$version"

    object Logging {
        const val version = "1.7.9"
        const val kotlinLogging = "io.github.microutils:kotlin-logging:$version"
    }
}

object KoTest {
    const val version = "4.2.0"

    // for kotest framework
    const val runner = "io.kotest:kotest-runner-junit5-jvm:$version"

    // for kotest core jvm assertion
    const val assertions = "io.kotest:kotest-assertions-core-jvm:$version"

    // for kotest property test
    const val property = "io.kotest:kotest-property-jvm:$version"

    // any other library
    fun library(name: String) = "io.kotest:kotest-$name:$version"
}

object Ktor {
    const val version = "1.6.7"
    const val server = "io.ktor:ktor-server:$version"
    const val serverNetty = "io.ktor:ktor-server-netty:$version"
    const val ktorTest = "io.ktor:ktor-server-test-host:$version"
    fun library(name: String) = "io.ktor:ktor-$name:$version"
}

object Mockk {
    const val version = "1.10.0"
    const val mockk = "io.mockk:mockk:$version"
}

object Ktlint {
    const val version = "0.38.1"
}

object Spotless {
    const val version = "5.1.0"
    const val spotless = "com.diffplug.spotless"
}

object Shadow {
    const val version = "5.2.0"
    const val shadow = "com.github.johnrengelman.shadow"
}
