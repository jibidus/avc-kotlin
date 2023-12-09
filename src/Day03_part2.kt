import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect

fun main() {
    fun answer(input: List<String>) = Engine(input).gearRatios.sum()

    val testInputPart1 = readInput("Day03_test")
    expect(
        answer(
            """
                ...
                ...
                ...
            """.trimIndent().lines()
        )
    ).toEqual(0)
    expect(
        answer(
            """
                ...
                .9.
                ...
            """.trimIndent().lines()
        )
    ).toEqual(0)
    expect(
        answer(
            """
                .*.
                .9.
                ...
            """.trimIndent().lines()
        )
    ).toEqual(0)
    expect(
        answer(
            """
                *..
                .9.
                ...
            """.trimIndent().lines()
        )
    ).toEqual(0)
    expect(
        answer(
            """
                *..
                .91
                ...
            """.trimIndent().lines()
        )
    ).toEqual(0)
    expect(
        answer(
            """
                ...*4
                .91..
                .....
            """.trimIndent().lines()
        )
    ).toEqual(4*91)
    expect(
        answer(
            """
                ...*4
                .91..
                *3...
            """.trimIndent().lines()
        )
    ).toEqual(4*91+3*91)

    expect(answer(testInputPart1)).toEqual(467835)
    "Tests are successful!".println()

    val input = readInput("Day03")
    println(answer(input))
}
