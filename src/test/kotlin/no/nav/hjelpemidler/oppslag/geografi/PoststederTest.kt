package no.nav.hjelpemidler.oppslag.geografi

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PoststederTest {
    private val poststeder = Poststeder()

    @Test
    fun hentPoststed() {
        val poststed = poststeder["0010"].poststed
        assertEquals("OSLO", poststed)
    }

    @Test
    fun hentAllePoststeder() {
        assertTrue(poststeder.size > 5000, "Antall poststeder var ${poststeder.size}")
    }
}
