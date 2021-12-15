package no.nav.hjelpemidler.oppslag

import com.natpryce.konfig.ConfigurationMap
import com.natpryce.konfig.ConfigurationProperties.Companion.systemProperties
import com.natpryce.konfig.EnvironmentVariables
import com.natpryce.konfig.Key
import com.natpryce.konfig.overriding
import com.natpryce.konfig.stringType

private val localProperties = ConfigurationMap(
    mapOf(
        "application.httpPort" to "8082",
        "application.profile" to "LOCAL",
        "NAIS_APP_NAME" to "digihot-oppslag",
        "NAIS_CLUSTER_NAME" to "dev-gcp",
        "NAIS_NAMESPACE" to "teamdigihot",
    )
)
private val devProperties = ConfigurationMap(
    mapOf(
        "application.profile" to "DEV",
    )
)
private val prodProperties = ConfigurationMap(
    mapOf(
        "application.profile" to "PROD",
    )
)

private val config = when (System.getenv("NAIS_CLUSTER_NAME") ?: System.getProperty("NAIS_CLUSTER_NAME")) {
    "dev-gcp" -> systemProperties() overriding EnvironmentVariables overriding devProperties
    "prod-gcp" -> systemProperties() overriding EnvironmentVariables overriding prodProperties
    else -> {
        systemProperties() overriding EnvironmentVariables overriding localProperties
    }
}

internal object Configuration {
    val application: Application = Application()

    data class Application(
        val profile: Profile = config[Key("application.profile", stringType)].let { Profile.valueOf(it) },
        val NAIS_APP_NAME: String? = config[Key("NAIS_APP_NAME", stringType)],
        val NAIS_CLUSTER_NAME: String? = config[Key("NAIS_CLUSTER_NAME", stringType)],
        val NAIS_NAMESPACE: String? = config[Key("NAIS_NAMESPACE", stringType)],
    )
}

enum class Profile {
    LOCAL, DEV, PROD
}
