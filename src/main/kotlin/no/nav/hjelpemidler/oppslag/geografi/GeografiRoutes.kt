package no.nav.hjelpemidler.oppslag.geografi

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

internal fun Route.geografiRoutes(bydelsnummer: Bydelsnummer, postnummer: Postnummer, kommunenummer: Kommunenummer) {
    get("/geografi/bydelsnr") {
        call.respond(bydelsnummer.hentAlleBydeler())
    }

    get("/geografi/bydelsnr/{bydelsnr}") {
        val bydelsnr = call.parameters["bydelsnr"]
        try {
            if (bydelsnr != null && bydelsnr.length == 6 && bydelsnr.all { it.isDigit() }) {
                call.respond(bydelsnummer.hentBydel(bydelsnr)!!)
            } else {
                throw RuntimeException("Feil ved oppslag på ugyldig bydelsnr $bydelsnr")
            }
        } catch (e: Exception) {
            logger.error(e) { "Feilet ved oppslag på bydelsnr $bydelsnr" }
            call.respond(HttpStatusCode.BadRequest, "Feil ved oppslag på bydelsnr $bydelsnr")
        }
    }

    get("/geografi/postnr") {
        call.respond(postnummer.hentAllePoststeder())
    }
    get("/geografi/postnr/{postnr}") {
        val postnr = call.parameters["postnr"]
        try {
            if (postnr != null && postnr.length == 4 && postnr.all { it.isDigit() }) {
                call.respond(postnummer.hentPoststed(postnr)!!)
            } else {
                throw RuntimeException("Feil ved oppslag på ugyldig postnr $postnr")
            }
        } catch (e: Exception) {
            logger.error(e) { "Feil ved oppslag på postnr $postnr" }
            call.respond(HttpStatusCode.BadRequest, "Feil ved oppslag på postnr $postnr")
        }
    }

    get("/geografi/kommunenr") {
        call.respond(kommunenummer.hentAlleKommuner())
    }

    get("/geografi/kommunenr/{kommunenr}") {
        val kommunenr = call.parameters["kommunenr"]
        try {
            if (kommunenr != null && kommunenr.length == 4 && kommunenr.all { it.isDigit() }) {
                call.respond(kommunenummer.hentKommuneOgFylke(kommunenr)!!)
            } else {
                throw RuntimeException("Feil ved oppslag på ugyldig kommunenr $kommunenr")
            }
        } catch (e: Exception) {
            logger.error(e) { "Feilet ved oppslag på kommunenr $kommunenr" }
            call.respond(HttpStatusCode.BadRequest, "Feil ved oppslag på kommunenr $kommunenr")
        }
    }
}
