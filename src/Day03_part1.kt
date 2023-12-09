import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect

fun main() {
    fun answer(input: List<String>) = Engine(input).partNumbers.sumOf { it.value.toInt() }

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
    ).toEqual(9)
    expect(
        answer(
            """
                *..
                .9.
                ...
            """.trimIndent().lines()
        )
    ).toEqual(9)
    expect(
        answer(
            """
                *..
                .91
                ...
            """.trimIndent().lines()
        )
    ).toEqual(91)
    expect(
        answer(
            """
                *...4
                .91.1
                .....
            """.trimIndent().lines()
        )
    ).toEqual(91)

    expect(answer(testInputPart1)).toEqual(4361)
    "Tests are successful!".println()

    val input = readInput("Day03")
    println(answer(input))
}

class Engine(val visualRepresentation: List<String>) {
    val maxX = visualRepresentation.maxOf { it.length }-1
    val maxY = visualRepresentation.size-1

    val numbers = visualRepresentation.flatMapIndexed { y, str -> str.findNumbers().map { Number(it.value, it.range, y,  this) } }
    val partNumbers = numbers.filter { it.isAdjacentToASymbol }

    fun pieceAt(x: Int, y: Int) = visualRepresentation[y][x]

    private fun String.findNumbers()  = "\\d+".toRegex().findAll(this)
}

class Number(val value: String, val xRange: IntRange, val y: Int, engine: Engine) {
    val adjacentPieces = HorizontalRange(xRange, y, engine).adjacentCoordinates.map { it.piece}
    val isAdjacentToASymbol = adjacentPieces.any { it.isASymbol }

    private val Char.isASymbol: Boolean
        get() = this != '.' && !this.isDigit()

    override fun toString() = value
}

data class Coordinate(val x: Int, val y: Int,val engine: Engine) {
    val isInEngine = x >=0 && y >= 0 && x <= engine.maxX && y <= engine.maxY
    val piece  by lazy {engine.pieceAt(x,y)}
}

data class HorizontalRange(val x: IntRange, val y: Int, val  engine: Engine) {
    val adjacentsXRange = x.first - 1..x.last+1
    val adjacentCoordinates = (y-1..y+1).flatMap { y -> adjacentsXRange.map {x -> Coordinate(x,y,engine) } }
        .filter { it.isInEngine }
}
