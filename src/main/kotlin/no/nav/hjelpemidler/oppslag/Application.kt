package no.nav.hjelpemidler.oppslag

import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.request.path
import io.ktor.server.routing.IgnoreTrailingSlash
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import no.nav.hjelpemidler.oppslag.geografi.Bydeler
import no.nav.hjelpemidler.oppslag.geografi.Kommuner
import no.nav.hjelpemidler.oppslag.geografi.Poststeder
import no.nav.hjelpemidler.oppslag.geografi.geografiRoutes
import org.slf4j.event.Level

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

fun Application.main() {
    install(ContentNegotiation) {
        jackson()
    }

    install(CallLogging) {
        level = Level.TRACE
        filter { call -> !call.request.path().startsWith("/internal") }
    }

    install(IgnoreTrailingSlash)

    val bydeler = Bydeler()
    val poststeder = Poststeder()
    val kommuner = Kommuner()

    routing {
        swaggerUI(path = "swagger")
        route("/api") {
            geografiRoutes(bydeler, kommuner, poststeder)
        }
    }
}
