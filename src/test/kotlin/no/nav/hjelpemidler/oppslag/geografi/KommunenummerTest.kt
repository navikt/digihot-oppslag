package no.nav.hjelpemidler.oppslag.geografi

import org.junit.Assert
import org.junit.jupiter.api.Test

internal class KommunenummerTest {

    private val kommunenr = Kommunenummer()

    @Test
    fun hentKommuneOgFylke() {
        val kommune = kommunenr.hentKommuneOgFylke("3004")
        Assert.assertEquals("Viken", kommune?.fylkenavn)
        Assert.assertEquals("Fredrikstad", kommune?.kommunenavn)
    }

    @Test
    fun hentAlleKommuner() {
        val antallKommuner = kommunenr.hentAlleKommuner().size
        Assert.assertEquals(356, antallKommuner)
    }
}
