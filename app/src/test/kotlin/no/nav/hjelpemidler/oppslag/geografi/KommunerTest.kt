package no.nav.hjelpemidler.oppslag.geografi

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class KommunerTest {
    private val kommuner = Kommuner()

    @Test
    fun hentKommune() {
        assertSoftly(kommuner["3107"]) {
            fylkesnavn shouldBe "Østfold"
            kommunenavn shouldBe "Fredrikstad"
        }

        assertSoftly(kommuner["5610"]) {
            fylkesnavn shouldBe "Finnmark"
            fylkesnummer shouldBe "56"
            kommunenavn shouldBe "Karasjok"
        }

        assertSoftly(kommuner["5636"]) {
            kommunenavn shouldBe "Nesseby"
        }

        assertSoftly(kommuner["5632"]) {
            kommunenavn shouldBe "Båtsfjord"
        }

        assertSoftly(kommuner["5605"]) {
            kommunenavn shouldBe "Sør-Varanger"
        }
    }

    @Test
    fun hentAlleKommuner() {
        kommuner shouldHaveSize 357
    }
}
