package no.nav.hjelpemidler.oppslag.geografi

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

fun Route.geografiRoutes(bydeler: Bydeler, kommuner: Kommuner, poststeder: Poststeder) {
    route("/geografi") {
        route("/bydeler") {
            bydelerRoutes(bydeler)
        }
        // fixme -> fjern og bruk "/bydeler"
        route("/bydelsnr") {
            bydelerRoutes(bydeler)
        }

        route("/kommuner") {
            kommunerRoutes(kommuner)
        }
        // fixme -> fjern og bruk "/kommuner"
        route("/kommunenr") {
            kommunerRoutes(kommuner)
        }

        route("/poststeder") {
            poststederRoutes(poststeder)
        }
        // fixme -> fjern og bruk "/poststeder"
        route("/postnr") {
            poststederRoutes(poststeder)
        }
    }
}

private fun Route.bydelerRoutes(bydeler: Bydeler) {
    get {
        call.respond(bydeler.toMap())
    }
    get("/{bydelsnummer}") {
        try {
            val bydelsnummer = call
                .parameters["bydelsnummer"]
                .requireNumberWithLength(6)
            call.respond(bydeler[bydelsnummer])
        } catch (e: IllegalArgumentException) {
            log.error(e) { "Ugyldig input i URL" }
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}

private fun Route.kommunerRoutes(kommuner: Kommuner) {
    get {
        call.respond(kommuner.toMap())
    }
    get("/{kommunenummer}") {
        try {
            val kommunenummer = call
                .parameters["kommunenummer"]
                .requireNumberWithLength(4)
            call.respond(kommuner[kommunenummer])
        } catch (e: IllegalArgumentException) {
            log.error(e) { "Ugyldig input i URL" }
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}

private fun Route.poststederRoutes(poststeder: Poststeder) {
    get {
        call.respond(poststeder.toMap())
    }
    get("/{postnummer}") {
        try {
            val postnummer = call
                .parameters["postnummer"]
                .requireNumberWithLength(4)
            call.respond(poststeder[postnummer])
        } catch (e: IllegalArgumentException) {
            log.error(e) { "Ugyldig input i URL" }
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
