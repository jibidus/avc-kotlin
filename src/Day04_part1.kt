import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.math.pow

fun main() {
    fun answer(input: List<String>) = input.map { Card(it) }.sumOf { it.points }

    assertThat(
        answer(listOf("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"))
    ).isEqualTo(8)
    assertThat(
        answer(listOf("Card 1: 41 48 83 86 17 | 1"))
    ).isEqualTo(0)
    assertThat(
        answer(listOf("Card 1: 41 48 83 86 17 | "))
    ).isEqualTo(0)

    assertThat(
        answer(listOf("Card 1: 2 48 1 86 17 |  1  2"))
    ).isEqualTo(2)

    val testInputPart1 = readInput("Day04_test")
    assertThat(answer(testInputPart1)).isEqualTo(13)
    "Tests are successful!".println()

    val input = readInput("Day04")
    println(answer(input))
    // 143966
}

class Card(input: String) {
    val values = input.substringAfter(":").split("|")
    val winningNumbers = values[0].trim().split("\\s+".toRegex())
    val availableNumbers = values[1].trim().split("\\s+".toRegex())
    val nbMatchingCards = availableNumbers.count { winningNumbers.contains(it) }
    val points:Int = 2.0.pow(nbMatchingCards-1).toInt()
}
