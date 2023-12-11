import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.math.min

fun main() {
    fun answer(input: List<String>): Long {
        val almanac = Almanac.parse(input)
        almanac.checkValidity()

        val nbSeeds = almanac.rangedSeeds().map { it.last - it.first }.sum()
        println("$nbSeeds seeds to test")

        var m: Long = 999_999_999
        var nbTestedSeeds = 0
        for (rangedSeed in almanac.rangedSeeds()) {
            rangedSeed.forEach {
                nbTestedSeeds++
                if (nbTestedSeeds%10_000_000==0) {
                    println("$nbTestedSeeds/$nbSeeds tested")
                }
               val v = almanac.convertSeed(it, "location")
                m = min(m , v)
            }
        }
        return m
    }

    val testInputPart1 = readInput("Day05_test")
    assertThat(answer(testInputPart1)).isEqualTo(46)
    "Tests are successful!".println()

    val input = readInput("Day05")
    println(answer(input))
}
