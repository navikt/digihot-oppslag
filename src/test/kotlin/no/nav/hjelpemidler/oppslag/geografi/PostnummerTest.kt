package no.nav.hjelpemidler.oppslag.geografi

import org.junit.Assert
import org.junit.jupiter.api.Test

internal class PostnummerTest {

    @Test
    fun hentPoststed() {
        val poststed = Postnummer.hentPoststed("0010")?.poststed
        Assert.assertEquals("OSLO", poststed)
    }

    @Test
    fun hentAllePoststeder() {
        val antallPoststeder = Postnummer.hentAllePoststeder().size
        println("Antall poststeder: $antallPoststeder")
        Assert.assertTrue(antallPoststeder > 5000)
    }
}
