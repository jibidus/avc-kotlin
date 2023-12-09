import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import kotlin.math.min

fun main() {
    fun answer(input: List<String>): Int {
        val cardCopies = input.map { CardCopy(Card(it), 1) }
        for ((index, cardCopy) in cardCopies.withIndex()) {
            for (j in index + 1..min(index + cardCopy.card.nbMatchingCards, cardCopies.size - 1)) {
                cardCopies[j].makeCopies(cardCopy.copies)
            }
        }
        return cardCopies.sumOf { it.copies }
    }

    expect(
        answer(
            listOf(
                "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
                "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19"
            )
        )
    ).toEqual(3)

    val testInputPart1 = readInput("Day04_test")
    expect(answer(testInputPart1)).toEqual(30)
    "Tests are successful!".println()

    val input = readInput("Day04")
    println(answer(input))
}

class CardCopy(val card: Card, var copies: Int) {
    fun makeCopies(copies: Int) {
        this.copies += copies
    }
}