package no.nav.hjelpemidler.oppslag.geografi

import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.Locale

/**
 * Source:
 * - https://www.kartverket.no/til-lands/kommunereform/tekniske-endringer-ved-sammenslaing-og-grensejustering/komendr2020
 *      Lagret og eksportert til .csv
 */

private const val KOMMUNENR_FIL = "geografi/kommunenr.csv"

class Kommunenummer {

    private val kommuneTabell: MutableMap<String, KommunenrDto> = mutableMapOf()

    fun hentKommuneOgFylke(kommunenr: String?): KommunenrDto? {
        return kommuneTabell[kommunenr]
    }

    fun hentAlleKommuner(): Map<String, KommunenrDto> {
        return kommuneTabell.toMap()
    }

    fun String.capitalizeWords() = lowercase().mapIndexed { index, letter ->
        when {
            index == 0 -> letter.titlecase(Locale.getDefault())
            setOf(' ', '-', '(').contains(this[index - 1]) -> letter.titlecase(Locale.getDefault())
            else -> letter
        }
    }.joinToString("").replace(" Og ", " og ")

    init {
        // Pga kommunesammenslåing er det duplikater i 2020 kolonnene (pga filen inneholder også linjer for 2019). Duplikater blir bare overskrevet.
        val csvSplitBy = ";"
        javaClass.classLoader.getResourceAsStream(KOMMUNENR_FIL).bufferedReader(StandardCharsets.UTF_8)
            .lines()
            .skip(1) // Hopp over header på første linje
            .forEach { line ->
                val splitLine = line.split(csvSplitBy).toTypedArray()
                val kommunenr: String = splitLine[6]
                val kommune: String = splitLine[7].capitalizeWords()
                val fylkenr: String = splitLine[4]
                val fylke: String = splitLine[5].capitalizeWords()

                val kommunenrIsValid = kommunenr.length == 4 && kommunenr.all { it.isDigit() }
                if (kommunenrIsValid) {
                    kommuneTabell[kommunenr] = KommunenrDto(kommune, fylkenr, fylke)
                } else {
                    throw IOException("There was an error parsing post data from file $KOMMUNENR_FIL for kommunenr '$kommunenr'")
                }
            }
    }
}

data class KommunenrDto(
    val kommunenavn: String,
    val fylkenummer: String,
    val fylkenavn: String,
)
