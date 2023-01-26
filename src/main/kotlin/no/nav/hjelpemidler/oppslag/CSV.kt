package no.nav.hjelpemidler.oppslag

import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvParser
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.fasterxml.jackson.module.kotlin.kotlinModule

val csvMapper: CsvMapper = CsvMapper.builder()
    .addModule(kotlinModule())
    .enable(CsvParser.Feature.TRIM_SPACES)
    .enable(CsvParser.Feature.SKIP_EMPTY_LINES)
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
