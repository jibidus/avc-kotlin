import assertk.assertThat
import assertk.assertions.isEqualTo

fun main() {
    fun answer(input: List<String>): Long {
        val almanac = Almanac.parse(input)
        almanac.checkValidity()
        return almanac.rangedSeeds().flatMap { it.map { nb -> almanac.convertSeed(nb, "location") } }.min()
    }

    val testInputPart1 = readInput("Day05_test")
    assertThat(answer(testInputPart1)).isEqualTo(46)
    "Tests are successful!".println()

    val input = readInput("Day05")
    println(answer(input))
}
