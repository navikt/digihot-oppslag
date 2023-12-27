package no.nav.hjelpemidler.oppslag.geografi

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import io.ktor.server.plugins.NotFoundException
import no.nav.hjelpemidler.oppslag.readCsv

/**
 * [Kilde](https://data.norge.no/datasets/44f30e8d-b653-4463-9e78-73aa7fbcfdf0)
 */
class Bydeler private constructor(
    private val bydelByBydelsnummer: Map<String, BydelDto>,
) : Map<String, BydelDto> by bydelByBydelsnummer {
    constructor() : this(
        readCsv<BydelCsv>("/geografi/bydeler.csv") { schema ->
            schema
                .withoutHeader()
                .withColumnSeparator(';')
        }.filterNot {
            it.bydelsnavn == "Uoppgitt"
        }.map {
            it.toDto()
        }.associateBy {
            it.bydelsnummer
        },
    )

    override operator fun get(key: String): BydelDto =
        bydelByBydelsnummer[key] ?: throw NotFoundException("Fant ikke bydel med bydelsnummer: '$key'")
}

@JsonPropertyOrder("bydelsnummer", "bydelsnavn")
data class BydelCsv(
    val bydelsnummer: String,
    val bydelsnavn: String,
) {
    init {
        bydelsnummer.requireNumberWithLength(BYDELSNUMMER_LENGDE)
        require(bydelsnavn.isNotBlank())
    }

    fun toDto(): BydelDto =
        BydelDto(
            bydelsnummer = bydelsnummer,
            bydelsnavn = bydelsnavn,
        )
}

data class BydelDto(
    val bydelsnummer: String,
    val bydelsnavn: String,
)
