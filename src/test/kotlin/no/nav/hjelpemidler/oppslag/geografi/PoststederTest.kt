package no.nav.hjelpemidler.oppslag.geografi

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class PoststederTest {
    private val poststeder = Poststeder()

    @Test
    fun hentPoststed() {
        val poststed = poststeder["0010"].poststed

        poststed shouldBe "OSLO"
    }

    @Test
    fun hentAllePoststeder() {
        // assertTrue(poststeder.size > 5000, "Antall poststeder var ${poststeder.size}")
        (poststeder.size > 5000) shouldBe true
    }
}
