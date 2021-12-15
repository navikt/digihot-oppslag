package no.nav.hjelpemidler.oppslag.geografi

import java.io.IOException
import java.nio.charset.StandardCharsets

/**
 * Source:
 * - https://www.kartverket.no/til-lands/kommunereform/tekniske-endringer-ved-sammenslaing-og-grensejustering/komendr2020
 *      Lagret og eksportert til .csv
 */
object Kommunenummer {

    private const val KOMMUNENR_FIL = "geografi/kommunenr.csv"
    private val kommuneTabell: MutableMap<String, KommunenrDto> = mutableMapOf()

    fun hentKommuneOgFylke(kommunenr: String?): KommunenrDto? {
        return kommuneTabell[kommunenr]
    }

    init {
        val csvSplitBy = ";"
        javaClass.classLoader.getResourceAsStream(KOMMUNENR_FIL).bufferedReader(StandardCharsets.UTF_8)
            .forEachLine { line ->
                val splitLine = line.split(csvSplitBy).toTypedArray()
                val kommunenr: String? = splitLine[6]
                val kommune: String? = splitLine[7]
                val fylkenr: String? = splitLine[4]
                val fylke: String? = splitLine[5]

                if (kommunenr != null && kommune != null && fylkenr != null && fylke != null) {
                    kommuneTabell[kommunenr] = KommunenrDto(kommune, fylkenr, fylke)
                } else {
                    throw IOException("There was an error parsing post data from file $KOMMUNENR_FIL for kommunenr $kommunenr")
                }
            }
    }
}

data class KommunenrDto(
    val kommunenavn: String,
    val fylkenummer: String,
    val fylkenavn: String,
)
