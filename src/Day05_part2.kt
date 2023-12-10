import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect

fun main() {
    fun answer(input: List<String>): Long {
        val almanac = Almanac.parse(input)
        almanac.checkValidity()
        return almanac.rangedSeeds().flatMap { it.map { nb -> almanac.convertSeed(nb, "location") } }.min()
    }

    val testInputPart1 = readInput("Day05_test")
    expect(answer(testInputPart1)).toEqual(46)
    "Tests are successful!".println()

    val input = readInput("Day05")
    println(answer(input))
}
