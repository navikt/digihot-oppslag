package no.nav.hjelpemidler.oppslag

import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.path
import io.ktor.server.routing.IgnoreTrailingSlash
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import no.nav.hjelpemidler.oppslag.geografi.Bydelsnummer
import no.nav.hjelpemidler.oppslag.geografi.Kommunenummer
import no.nav.hjelpemidler.oppslag.geografi.Postnummer
import no.nav.hjelpemidler.oppslag.geografi.geografiRoutes
import org.slf4j.event.Level

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        jackson()
    }

    install(CallLogging) {
        level = Level.TRACE
        filter { call -> !call.request.path().startsWith("/internal") }
    }

    install(IgnoreTrailingSlash)
    internal()

    val bydelsnummer = Bydelsnummer()
    val postnummer = Postnummer()
    val kommunenummer = Kommunenummer()

    routing {
        route("/api") {
            geografiRoutes(bydelsnummer, postnummer, kommunenummer)
        }
    }
}
