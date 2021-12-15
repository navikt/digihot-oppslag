package no.nav.hjelpemidler.oppslag.geografi

import java.io.IOException
import java.nio.charset.StandardCharsets

/**
 * Source: https://www.bring.no/radgivning/sende-noe/adressetjenester/postnummer
 */
object Postnummer {
    private const val FILENAME = "geografi/postal_codes_no.tsv"
    private val postalCodeTable: MutableMap<String, PostnrDto> = mutableMapOf()

    fun hentPoststed(postalCode: String?): PostnrDto? {
        return postalCodeTable[postalCode]
    }

    init {
        val csvSplitBy = "\t"
        javaClass.classLoader.getResourceAsStream(FILENAME)?.bufferedReader(StandardCharsets.UTF_8)
            ?.forEachLine { line ->
                val splitLine = line.split(csvSplitBy).toTypedArray()
                val postnummer: String = splitLine[0]
                val poststed: String = splitLine[1]
                val kommunenummer: String = splitLine[2]
                val kommunenavn: String = splitLine[3]

                if (postnummer.isNotBlank() && poststed.isNotBlank() && kommunenummer.isNotBlank() && kommunenavn.isNotBlank()) {
                    postalCodeTable[postnummer] = PostnrDto(poststed, kommunenummer, kommunenavn)
                } else {
                    throw IOException("There was an error parsing post data from file for postal code {}")
                }
            }
    }
}

data class PostnrDto(
    val poststed: String,
    val kommunenr: String,
    val kommunenavn: String,
)
