package no.nav.hjelpemidler.oppslag

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get

fun Route.internal() {
    get("/is_alive") {

        call.respondText("ALIVE", ContentType.Text.Plain)
    }
    get("/is_ready") {

        call.respondText("READY", ContentType.Text.Plain)
    }
}
