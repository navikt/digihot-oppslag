package no.nav.hjelpemidler.oppslag.geografi

import kotlin.test.Test
import kotlin.test.assertEquals

internal class BydelsnummerTest {

    private val bydelsnummer = Bydelsnummer()

    @Test
    fun hentBydel() {
        val bydel = bydelsnummer.hentBydel("030105")?.bydelsnavn
        assertEquals("Frogner", bydel)
    }

    @Test
    fun hentAlleBydeler() {
        val antallBydeler = bydelsnummer.hentAlleBydeler().size
        assertEquals(38, antallBydeler)
    }
}
