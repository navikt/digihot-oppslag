package no.nav.hjelpemidler.oppslag.geografi

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import io.ktor.server.plugins.NotFoundException
import no.nav.hjelpemidler.oppslag.readCsv

/**
 * [Kilde](https://www.bring.no/radgivning/sende-noe/adressetjenester/postnummer)
 */
class Poststeder private constructor(
    private val poststedByPostnummer: Map<String, PoststedDto>,
) : Map<String, PoststedDto> by poststedByPostnummer {
    constructor() : this(
        readCsv<PoststedCsv>("/geografi/poststeder.tsv") { schema ->
            schema
                .withoutHeader()
                .withColumnSeparator('\t')
        }.map {
            it.toDto()
        }.associateBy {
            it.postnummer
        },
    )

    override operator fun get(key: String): PoststedDto =
        poststedByPostnummer[key] ?: throw NotFoundException("Fant ikke poststed med postnummer: '$key'")
}

@JsonPropertyOrder("postnummer", "poststed", "kommunenummer", "kommunenavn", "kategori")
data class PoststedCsv(
    val postnummer: String,
    val poststed: String,
    val kommunenummer: String,
    val kommunenavn: String,
    val kategori: String,
) {
    init {
        postnummer.requireNumberWithLength(4)
        require(poststed.isNotBlank())
        kommunenummer.requireNumberWithLength(4)
        require(kommunenavn.isNotBlank())
        require(kategori.isNotBlank() && kategori.length == 1)
    }

    fun toDto(): PoststedDto = PoststedDto(
        postnummer = postnummer,
        poststed = poststed,
        kommunenummer = kommunenummer,
        kommunenavn = kommunenavn,
    )
}

data class PoststedDto(
    val postnummer: String,
    val poststed: String,
    val kommunenummer: String,
    val kommunenavn: String,
) {
    @Deprecated("Bruk kommunenummer")
    val kommunenr = kommunenummer
}
