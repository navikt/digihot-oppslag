package no.nav.hjelpemidler.oppslag.geografi

import kotlin.test.Test
import kotlin.test.assertEquals

internal class KommunenummerTest {

    private val kommunenr = Kommunenummer()

    @Test
    fun hentKommuneOgFylke() {
        var kommune = kommunenr.hentKommuneOgFylke("3004")
        assertEquals("Viken", kommune?.fylkenavn)
        assertEquals("Fredrikstad", kommune?.kommunenavn)

        kommune = kommunenr.hentKommuneOgFylke("5437")
        assertEquals("Troms og Finnmark", kommune?.fylkenavn, "Kontroller stor og liten forbokstav")
        assertEquals("54", kommune?.fylkenummer)
        assertEquals("Karasjok", kommune?.kommunenavn, "Bruk norsk variant av navn, ikke samisk.")

        kommune = kommunenr.hentKommuneOgFylke("5442")
        assertEquals("Nesseby", kommune?.kommunenavn)

        kommune = kommunenr.hentKommuneOgFylke("5443")
        assertEquals("Båtsfjord", kommune?.kommunenavn)

        kommune = kommunenr.hentKommuneOgFylke("5444")
        assertEquals("Sør-Varanger", kommune?.kommunenavn, "Kontroller stor forbokstav og bindestrek")
    }

    @Test
    fun hentAlleKommuner() {
        val antallKommuner = kommunenr.hentAlleKommuner().size
        assertEquals(356, antallKommuner)
    }
}
