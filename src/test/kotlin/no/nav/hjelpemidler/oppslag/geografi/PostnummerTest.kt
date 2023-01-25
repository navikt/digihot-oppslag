package no.nav.hjelpemidler.oppslag.geografi

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class PostnummerTest {

    private val postnummer = Postnummer()

    @Test
    fun hentPoststed() {
        val poststed = postnummer.hentPoststed("0010")?.poststed
        assertEquals("OSLO", poststed)
    }

    @Test
    fun hentAllePoststeder() {
        val antallPoststeder = postnummer.hentAllePoststeder().size
        println("Antall poststeder: $antallPoststeder")
        assertTrue(antallPoststeder > 5000)
    }
}
