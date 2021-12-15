package no.nav.hjelpemidler.oppslag.geografi

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

internal fun Route.geografiRoutes() {
    get("/geografi/postnr/{postnr}") {
        val postnr = call.parameters["postnr"]
        try {
            if (postnr != null && postnr.length == 4 && postnr.all { it.isDigit() }) {
                call.respond(Postnummer.hentPoststed(postnr)!!)
            } else {
                throw RuntimeException("Feil ved oppslag på ugyldig postnr $postnr")
            }
        } catch (e: Exception) {
            logger.error(e) { "Feil ved oppslag på postnr $postnr" }
            call.respond(HttpStatusCode.BadRequest, "Feil ved oppslag på postnr $postnr")
        }
    }
    get("/geografi/kommunenr/{kommunenr}") {
        val kommunenr = call.parameters["kommunenr"]
        try {
            if (kommunenr != null && kommunenr.length == 4 && kommunenr.all { it.isDigit() }) {
                call.respond(Kommunenummer.hentKommuneOgFylke(kommunenr)!!)
            } else {
                throw RuntimeException("Feil ved oppslag på ugyldig kommunenr $kommunenr")
            }
        } catch (e: Exception) {
            logger.error(e) { "Feilet ved oppslag på kommunenr $kommunenr" }
            call.respond(HttpStatusCode.BadRequest, "Feil ved oppslag på kommunenr $kommunenr")
        }
    }
}
