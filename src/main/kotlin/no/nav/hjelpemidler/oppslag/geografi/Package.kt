package no.nav.hjelpemidler.oppslag.geografi

fun String?.requireNumberWithLength(length: Int): String {
    require(this != null && this.length == length && this.all { it.isDigit() }) {
        "'$this' er ugyldig"
    }
    return this
}

val FYLKESNUMMER_LENGDE = 2
val KOMMUNENUMMER_LENGDE = 4
val POSTNUMMER_LENGDE = 4
val BYDELSNUMMER_LENGDE = 6
