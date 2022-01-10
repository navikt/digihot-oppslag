package no.nav.hjelpemidler.oppslag.geografi

import org.junit.Assert
import org.junit.jupiter.api.Test

internal class BydelsnummerTest {

    private val bydelsnummer = Bydelsnummer()

    @Test
    fun hentBydel() {
        val bydel = bydelsnummer.hentBydel("030105")?.bydelsnavn
        Assert.assertEquals("Frogner", bydel)
    }

    @Test
    fun hentAlleBydeler() {
        val antallBydeler = bydelsnummer.hentAlleBydeler().size
        Assert.assertEquals(42, antallBydeler)
    }
}
