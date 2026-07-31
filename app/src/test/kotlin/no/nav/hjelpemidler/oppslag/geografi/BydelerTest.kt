package no.nav.hjelpemidler.oppslag.geografi

import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class BydelerTest {
    private val bydeler = Bydeler()

    @Test
    fun hentBydel() {
        bydeler["030105"].bydelsnavn shouldBe "Frogner"
    }

    @Test
    fun hentAlleBydeler() {
        bydeler shouldHaveSize 38
    }
}
