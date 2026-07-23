package no.nav.hjelpemidler.oppslag.geografi

import no.nav.hjelpemidler.text.isInteger

fun String?.requireNumberWithLength(length: Int): String {
    require(!isNullOrBlank() && this.length == length && isInteger()) {
        "'$this' er ugyldig"
    }
    return this
}

val FYLKESNUMMER_LENGDE = 2
val KOMMUNENUMMER_LENGDE = 4
val POSTNUMMER_LENGDE = 4
val BYDELSNUMMER_LENGDE = 6
