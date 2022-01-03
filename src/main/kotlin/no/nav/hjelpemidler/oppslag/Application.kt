package no.nav.hjelpemidler.oppslag

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.jackson.JacksonConverter
import io.ktor.request.path
import io.ktor.routing.IgnoreTrailingSlash
import io.ktor.routing.route
import io.ktor.routing.routing
import no.nav.hjelpemidler.oppslag.geografi.Kommunenummer
import no.nav.hjelpemidler.oppslag.geografi.Postnummer
import no.nav.hjelpemidler.oppslag.geografi.geografiRoutes
import org.slf4j.event.Level

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {

    install(ContentNegotiation) {
        register(ContentType.Application.Json, JacksonConverter(JacksonMapper.objectMapper))
    }

    install(CallLogging) {
        level = Level.TRACE
        filter { call -> !call.request.path().startsWith("/internal") }
    }

    install(IgnoreTrailingSlash)

    val postnummer = Postnummer()
    val kommunenummer = Kommunenummer()

    routing {
        internal()
        route("/api") {
            geografiRoutes(postnummer, kommunenummer)
        }
    }
}
