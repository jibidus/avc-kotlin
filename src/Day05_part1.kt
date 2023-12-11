import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.size

fun main() {

    fun answer(input: List<String>): Long {
        val almanac = Almanac.parse(input)
        almanac.checkValidity()
        return almanac.seeds.map { it.toLong() }.map { almanac.convertSeed(it, "location") }.min()
    }

    val testInputPart1 = readInput("Day05_test")
    val almanac = Almanac.parse(testInputPart1)
    almanac.checkValidity()
    assertThat(almanac.seeds).size().isEqualTo(4)
    assertThat(almanac.mappings).size().isEqualTo(7)
    val seedToSoil = almanac.mappings.first { it.source == "seed" && it.destination == "soil" }
    assertThat(seedToSoil.convert(0)).isEqualTo(0)
    assertThat(seedToSoil.convert(51)).isEqualTo(53)
    assertThat(seedToSoil.convert(99)).isEqualTo(51)
    assertThat(almanac.convertSeed(79, "soil")).isEqualTo(81)
    assertThat(answer(testInputPart1)).isEqualTo(35)
    "Tests are successful!".println()

    val input = readInput("Day05")
    println(answer(input))
}

class Almanac {
    var seeds = listOf<String>()
    val mappings = mutableListOf<Mapping>()

    fun convertSeed(seedNumber: Long, destinationCategory: String): Long {
        val finalComponent = mappings.fold(Component(seedNumber, "seed")) { c, mapping -> mapping.convert(c) }
        require(finalComponent.category == destinationCategory) {"Last mapper does not convert to expected category ($destinationCategory), but to ${finalComponent.category}"}
        return finalComponent.number
    }

    fun convert(component: Component): Component =
        mappings.first { it.source == component.category }.convert(component)

    fun checkValidity() {
        val duplicates = mappings.groupingBy { it.source }.eachCount().filter { it.value > 1 }
        require(duplicates.isEmpty()) { duplicates }

        var lastDestination : String? = null
        for (mapping in mappings) {
            if (lastDestination != null) {
                require(mapping.source == lastDestination)
            }
            lastDestination = mapping.destination
        }
    }

    fun rangedSeeds() = seeds.map { it.toLong() }.chunked(2).map { LongRange(it[0], it[0] + it[1] - 1) }

    companion object {
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
    }
}

data class Component(val number: Long, val category: String)

data class Mapping(val source: String, val destination: String) {
    val convertions = mutableListOf<Convertion>()

    fun register(destinationRangeStart: String, sourceRangeStart: String, rangeLength: String) =
        convertions.add(Convertion(destinationRangeStart.toLong(), sourceRangeStart.toLong(), rangeLength.toLong()))

    fun convert(sourceNumber: Long) =
        convertions.firstOrNull { it.canConvert(sourceNumber) }?.convert(sourceNumber) ?: sourceNumber

    fun convert(component: Component): Component {
//        require(component.category == source) { "$source to $destination cannot convert $component" }
        return Component(convert(component.number), destination)
    }
}

data class Convertion(val destinationRangeStart: Long, val sourceRangeStart: Long, val rangeLength: Long) {
    val sourceRange = sourceRangeStart..<sourceRangeStart + rangeLength
    fun canConvert(source: Long) = sourceRange.contains(source)
    fun convert(source: Long) = destinationRangeStart + source - sourceRangeStart
}