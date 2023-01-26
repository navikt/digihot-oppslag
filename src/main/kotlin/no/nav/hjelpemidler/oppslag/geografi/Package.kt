package no.nav.hjelpemidler.oppslag.geografi

fun String?.requireNumberWithLength(length: Int): String {
    require(this != null && this.length == length && this.all { it.isDigit() }) {
        "'$this' er ugyldig"
    }
    return this
}
