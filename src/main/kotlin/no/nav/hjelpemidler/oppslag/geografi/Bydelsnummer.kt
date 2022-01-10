package no.nav.hjelpemidler.oppslag.geografi

import java.io.IOException
import java.nio.charset.StandardCharsets

/**
 * Source: https://data.norge.no/datasets/44f30e8d-b653-4463-9e78-73aa7fbcfdf0
 */

private const val FILENAME = "geografi/bydelsnr.csv"

class Bydelsnummer {
    private val bydelsnummerTable: MutableMap<String, BydelsnummerDto> = mutableMapOf()

    fun hentBydel(bydelsnr: String?): BydelsnummerDto? {
        return bydelsnummerTable[bydelsnr]
    }

    fun hentAlleBydeler(): Map<String, BydelsnummerDto> {
        return bydelsnummerTable.toMap()
    }

    init {
        val csvSplitBy = ";"
        javaClass.classLoader.getResourceAsStream(FILENAME)?.bufferedReader(StandardCharsets.UTF_8)
            ?.forEachLine { line ->
                val splitLine = line.split(csvSplitBy).toTypedArray()
                val bydelsnummer: String = splitLine[0]
                val bydelsnavn: String = splitLine[1]

                if (bydelsnummer.isNotBlank() && bydelsnavn.isNotBlank()) {
                    if (bydelsnavn != "Uoppgitt") bydelsnummerTable[bydelsnummer] = BydelsnummerDto(bydelsnavn)
                } else {
                    throw IOException("There was an error parsing post data from file for bydelsnummer '$bydelsnummer'")
                }
            }
    }
}

data class BydelsnummerDto(
    val bydelsnavn: String,
)
