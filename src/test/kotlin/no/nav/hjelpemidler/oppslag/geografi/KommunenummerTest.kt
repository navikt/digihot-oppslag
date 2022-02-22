package no.nav.hjelpemidler.oppslag.geografi

import org.junit.Assert
import org.junit.jupiter.api.Test

internal class KommunenummerTest {

    private val kommunenr = Kommunenummer()

    @Test
    fun hentKommuneOgFylke() {
        var kommune = kommunenr.hentKommuneOgFylke("3004")
        Assert.assertEquals("Viken", kommune?.fylkenavn)
        Assert.assertEquals("Fredrikstad", kommune?.kommunenavn)

        kommune = kommunenr.hentKommuneOgFylke("5437")
        Assert.assertEquals("Troms og Finnmark", kommune?.fylkenavn)
        Assert.assertEquals("54", kommune?.fylkenummer)
        Assert.assertEquals("Karasjok", kommune?.kommunenavn)

        kommune = kommunenr.hentKommuneOgFylke("5442")
        Assert.assertEquals("Nesseby", kommune?.kommunenavn)

        kommune = kommunenr.hentKommuneOgFylke("5443")
        Assert.assertEquals("Båtsfjord", kommune?.kommunenavn)

        kommune = kommunenr.hentKommuneOgFylke("5444")
        Assert.assertEquals("Sør-Varanger", kommune?.kommunenavn)
    }

    @Test
    fun hentAlleKommuner() {
        val antallKommuner = kommunenr.hentAlleKommuner().size
        Assert.assertEquals(356, antallKommuner)
    }
}
