import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect

fun main() {
    fun parse(input: List<String>): Almanac {
        val almanac = Almanac()
        lateinit var currentMapping: Mapping
        for (line in input) {
            if (line.startsWith("seeds:")) {
                almanac.seeds = line.substring("seeds:".length).trim().split("\\s+".toRegex())
                continue
            }
            val result = "(.*)-to-(.*) map:".toRegex().find(line)
            if (result != null) {
                val (_, from, to) = result.groupValues
                currentMapping = Mapping(from, to)
                almanac.mappings += currentMapping
                continue
            }
            if (line.trim().isNotEmpty()) {
                val (destinationRangeStart, sourceRangeStart, rangeLength) = line.split("\\s+".toRegex())
                currentMapping.register(destinationRangeStart, sourceRangeStart, rangeLength)
            }
        }
        return almanac
    }

    fun answer(input: List<String>) = 1

    val testInputPart1 = readInput("Day05_test")
    val almanac = parse(testInputPart1)
    expect(almanac.seeds).toHaveSize(4)
    expect(almanac.mappings).toHaveSize(7)
    val seedToSoil = almanac.mappings.first { it.source == "seed" && it.destination == "soil" }
    expect(seedToSoil.convert(0)).toEqual(0)
    expect(seedToSoil.convert(51)).toEqual(53)
    expect(seedToSoil.convert(99)).toEqual(51)
//    expect(answer(testInputPart1)).toEqual(13)
    "Tests are successful!".println()

    val input = readInput("Day05")
    println(answer(input))
}

class Almanac {
    var seeds = listOf<String>()
    val mappings = mutableListOf<Mapping>()
}

class Mapping(val source: String, val destination: String) {
    val convertions = mutableListOf<Convertion>()

    fun register(destinationRangeStart: String, sourceRangeStart: String, rangeLength: String) =
        convertions.add(Convertion(destinationRangeStart.toInt(), sourceRangeStart.toInt(), rangeLength.toInt()))

    fun convert(sourceNumber: Int) =
        convertions.firstOrNull { it.canConvert(sourceNumber) }?.convert(sourceNumber) ?: sourceNumber
}

data class Convertion(val destinationRangeStart: Int, val sourceRangeStart: Int, val rangeLength: Int) {
    val sourceRange = sourceRangeStart..<sourceRangeStart + rangeLength
    fun canConvert(source: Int) = sourceRange.contains(source)
    fun convert(source: Int) = destinationRangeStart + source - sourceRangeStart
}