package no.nav.hjelpemidler.oppslag

import io.ktor.http.ContentType
import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.internal() {
    routing {
        route("/internal") {
            get("/is_alive") {
                call.respondText("ALIVE", ContentType.Text.Plain)
            }

            get("/is_ready") {
                call.respondText("READY", ContentType.Text.Plain)
            }
        }
    }
}
