package no.nav.hjelpemidler.oppslag.geografi

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import io.ktor.server.plugins.NotFoundException
import no.nav.hjelpemidler.oppslag.readCsv

/**
 * [Kilde](https://ws.geonorge.no/kommuneinfo/v1/#/default/get_fylkerkommuner)
 * Konvertert til .csv med Bash script
 */
class Kommuner private constructor(
    private val kommuneByKommunenummer: Map<String, KommuneDto>,
) : Map<String, KommuneDto> by kommuneByKommunenummer {
    constructor() : this(
        readCsv<KommuneCsv>("/geografi/kommuner.csv") { schema ->
            schema
                .withoutHeader()
                .withColumnSeparator(';')
        }.map {
            it.toDto()
        }.associateBy {
            it.kommunenummer
        },
    )

    override operator fun get(key: String): KommuneDto =
        kommuneByKommunenummer[key] ?: throw NotFoundException("Fant ikke kommune med kommunenummer: '$key'")
}

@JsonPropertyOrder("fylkesnummer", "fylkesnavn", "kommunenummer", "kommunenavn")
data class KommuneCsv(
    val fylkesnummer: String,
    val fylkesnavn: String,
    val kommunenummer: String,
    val kommunenavn: String,
) {
    init {
        fylkesnummer.requireNumberWithLength(FYLKESNUMMER_LENGDE)
        require(fylkesnavn.isNotBlank())
        kommunenummer.requireNumberWithLength(KOMMUNENUMMER_LENGDE)
        require(kommunenavn.isNotBlank())
    }

    fun toDto(): KommuneDto =
        KommuneDto(
            fylkesnummer = fylkesnummer,
            fylkesnavn = fylkesnavn,
            kommunenummer = kommunenummer,
            kommunenavn = kommunenavn,
        )
}

data class KommuneDto(
    val fylkesnummer: String,
    val fylkesnavn: String,
    val kommunenummer: String,
    val kommunenavn: String,
) {
    @Deprecated("Bruk fylkesnummer")
    val fylkenummer = fylkesnummer

    @Deprecated("Bruk fylkesnavn")
    val fylkenavn = fylkesnavn
}
