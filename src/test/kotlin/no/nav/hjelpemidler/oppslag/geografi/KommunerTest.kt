package no.nav.hjelpemidler.oppslag.geografi

import kotlin.test.Test
import kotlin.test.assertEquals

class KommunerTest {
    private val kommuner = Kommuner()

    @Test
    fun hentKommune() {
        var kommune = kommuner["3107"]
        assertEquals("Østfold", kommune.fylkesnavn)
        assertEquals("Fredrikstad", kommune.kommunenavn)

        kommune = kommuner["5610"]
        assertEquals("Finnmark", kommune.fylkesnavn, "Kontroller stor og liten forbokstav")
        assertEquals("56", kommune.fylkesnummer)
        assertEquals("Karasjok", kommune.kommunenavn, "Bruk norsk variant av navn, ikke samisk.")

        kommune = kommuner["5636"]
        assertEquals("Nesseby", kommune.kommunenavn)

        kommune = kommuner["5632"]
        assertEquals("Båtsfjord", kommune.kommunenavn)

        kommune = kommuner["5605"]
        assertEquals("Sør-Varanger", kommune.kommunenavn, "Kontroller stor forbokstav og bindestrek")
    }

    @Test
    fun hentAlleKommuner() {
        assertEquals(357, kommuner.size)
    }
}
