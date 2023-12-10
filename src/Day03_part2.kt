import assertk.assertThat
import assertk.assertions.isEqualTo

fun main() {
    fun answer(input: List<String>) = Engine(input).gearRatios.sum()

    val testInputPart1 = readInput("Day03_test")
    assertThat(
        answer(
            """
                ...
                ...
                ...
            """.trimIndent().lines()
        )
    ).isEqualTo(0)
    assertThat(
        answer(
            """
                ...
                .9.
                ...
            """.trimIndent().lines()
        )
    ).isEqualTo(0)
    assertThat(
        answer(
            """
                .*.
                .9.
                ...
            """.trimIndent().lines()
        )
    ).isEqualTo(0)
    assertThat(
        answer(
            """
                *..
                .9.
                ...
            """.trimIndent().lines()
        )
    ).isEqualTo(0)
    assertThat(
        answer(
            """
                *..
                .91
                ...
            """.trimIndent().lines()
        )
    ).isEqualTo(0)
    assertThat(
        answer(
            """
                ...*4
                .91..
                .....
            """.trimIndent().lines()
        )
    ).isEqualTo(4*91)
    assertThat(
        answer(
            """
                ...*4
                .91..
                *3...
            """.trimIndent().lines()
        )
    ).isEqualTo(4*91+3*91)

    assertThat(answer(testInputPart1)).isEqualTo(467835)
    "Tests are successful!".println()

    val input = readInput("Day03")
    println(answer(input))
}
