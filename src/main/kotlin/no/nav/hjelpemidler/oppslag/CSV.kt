package no.nav.hjelpemidler.oppslag

import tools.jackson.dataformat.csv.CsvMapper
import tools.jackson.dataformat.csv.CsvReadFeature
import tools.jackson.dataformat.csv.CsvSchema
import tools.jackson.module.kotlin.jacksonTypeRef
import tools.jackson.module.kotlin.kotlinModule

val csvMapper: CsvMapper =
    CsvMapper.builder()
        .addModule(kotlinModule())
        .enable(CsvReadFeature.TRIM_SPACES)
        .enable(CsvReadFeature.SKIP_EMPTY_LINES)
        .build()

inline fun <reified T : Any> readCsv(
    name: String,
    block: (CsvSchema) -> CsvSchema = { schema -> schema },
): List<T> {
    val typeReference = jacksonTypeRef<T>()
    val schema = csvMapper.schemaFor(typeReference).run(block)
    return checkNotNull(T::class.java.getResourceAsStream(name)).use { inputStream ->
        csvMapper
            .readerFor(typeReference)
            .with(schema)
            .readValues<T>(inputStream)
            .readAll()
    }
}
