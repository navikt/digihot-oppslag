package no.nav.hjelpemidler.oppslag.geografi

import kotlin.test.Test
import kotlin.test.assertEquals

internal class BydelerTest {
    private val bydeler = Bydeler()

    @Test
    fun hentBydel() {
        val bydel = bydeler["030105"].bydelsnavn
        assertEquals("Frogner", bydel)
    }

    @Test
    fun hentAlleBydeler() {
        assertEquals(38, bydeler.size)
    }
}
