package no.nav.hjelpemidler.oppslag.client

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.http.ContentType
import no.nav.hjelpemidler.configuration.EnvironmentVariable
import no.nav.hjelpemidler.domain.geografi.Bydel
import no.nav.hjelpemidler.domain.geografi.Kommune
import no.nav.hjelpemidler.http.createHttpClient
import java.io.Closeable

private val log = KotlinLogging.logger {}

class OppslagClient(
    engine: HttpClientEngine,
    private val baseUrl: String = DIGIHOT_OPPSLAG_BASE_URL,
) : Closeable {
    private val client = createHttpClient(engine) {
        expectSuccess = true
        install(HttpRequestRetry) {
            retryOnExceptionOrServerErrors(maxRetries = 5)
            exponentialDelay()
        }
        defaultRequest {
            accept(ContentType.Application.Json)
        }
    }

    suspend fun hentKommune(nummer: String): Kommune = try {
        client.get("$baseUrl/geografi/kommuner/$nummer").body()
    } catch (e: ResponseException) {
        log.error(e) { "Kunne ikke hente kommune med nummer: '$nummer' fra DigiHoT Oppslag" }
        Kommune(nummer = nummer, navn = nummer)
    }

    suspend fun hentAlleKommuner(): Map<String, Kommune> = try {
        client.get("$baseUrl/geografi/kommuner").body()
    } catch (e: ResponseException) {
        log.error(e) { "Kunne ikke hente kommuner fra DigiHoT Oppslag" }
        emptyMap()
    }

    suspend fun hentBydel(nummer: String): Bydel = try {
        client.get("$baseUrl/geografi/bydeler/$nummer").body()
    } catch (e: ResponseException) {
        log.error(e) { "Kunne ikke hente bydel med nummer: '$nummer' fra DigiHoT Oppslag" }
        Bydel(nummer = nummer, navn = nummer)
    }

    suspend fun hentAlleBydeler(): Map<String, Bydel> = try {
        client.get("$baseUrl/geografi/bydeler").body()
    } catch (e: ResponseException) {
        log.error(e) { "Kunne ikke hente bydeler fra DigiHoT Oppslag" }
        emptyMap()
    }

    override fun close() {
        client.close()
    }

    companion object {
        val DIGIHOT_OPPSLAG_BASE_URL by EnvironmentVariable
    }
}
