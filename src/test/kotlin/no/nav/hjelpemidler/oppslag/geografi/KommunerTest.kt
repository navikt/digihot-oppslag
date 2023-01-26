package no.nav.hjelpemidler.oppslag.geografi

import kotlin.test.Test
import kotlin.test.assertEquals

class KommunerTest {
    private val kommuner = Kommuner()

    @Test
    fun hentKommune() {
        var kommune = kommuner["3004"]
        assertEquals("Viken", kommune.fylkesnavn)
        assertEquals("Fredrikstad", kommune.kommunenavn)

        kommune = kommuner["5437"]
        assertEquals("Troms og Finnmark", kommune.fylkesnavn, "Kontroller stor og liten forbokstav")
        assertEquals("54", kommune.fylkesnummer)
        assertEquals("Karasjok", kommune.kommunenavn, "Bruk norsk variant av navn, ikke samisk.")

        kommune = kommuner["5442"]
        assertEquals("Nesseby", kommune.kommunenavn)

        kommune = kommuner["5443"]
        assertEquals("Båtsfjord", kommune.kommunenavn)

        kommune = kommuner["5444"]
        assertEquals("Sør-Varanger", kommune.kommunenavn, "Kontroller stor forbokstav og bindestrek")
    }

    @Test
    fun hentAlleKommuner() {
        assertEquals(356, kommuner.size)
    }
}
