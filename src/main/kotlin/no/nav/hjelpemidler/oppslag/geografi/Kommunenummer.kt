package no.nav.hjelpemidler.oppslag.geografi

import java.io.IOException
import java.nio.charset.StandardCharsets

/**
 * Kilde: https://ws.geonorge.no/kommuneinfo/v1/#/default/get_fylkerkommuner
 * Konvertert til .csv med Bash script
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

    init {
        val csvSplitBy = ";"
        javaClass.classLoader.getResourceAsStream(KOMMUNENR_FIL).bufferedReader(StandardCharsets.UTF_8)
            .lines()
            .forEach { line ->
                val splitLine = line.split(csvSplitBy).toTypedArray()
                val fylkenr: String = splitLine[0]
                val fylke: String = splitLine[1]
                val kommunenr: String = splitLine[2]
                val kommune: String = splitLine[3]

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
