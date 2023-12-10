import assertk.assertThat
import assertk.assertions.*

fun main() {
    fun answer(input: List<String>) = Engine(input).partNumbers.sumOf { it.value.toInt() }

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
    ).isEqualTo(9)
    assertThat(
        answer(
            """
                *..
                .9.
                ...
            """.trimIndent().lines()
        )
    ).isEqualTo(9)
    assertThat(
        answer(
            """
                *..
                .91
                ...
            """.trimIndent().lines()
        )
    ).isEqualTo(91)
    assertThat(
        answer(
            """
                *...4
                .91.1
                .....
            """.trimIndent().lines()
        )
    ).isEqualTo(91)

    assertThat(answer(testInputPart1)).isEqualTo(4361)
    "Tests are successful!".println()

    val input = readInput("Day03")
    println(answer(input))
}

class Engine(val visualRepresentation: List<String>) {
    val maxX = visualRepresentation.maxOf { it.length }-1
    val maxY = visualRepresentation.size-1

    val numbers = visualRepresentation.flatMapIndexed { y, str -> str.findNumbers().map { Number(it.value, it.range, y,  this) } }
    val partNumbers = numbers.filter { it.isAdjacentToASymbol }

    val stars = visualRepresentation.flatMapIndexed { y, str -> str.findStars().map { Star(it.value, it.range.first, y,  this) } }
    val gearRatios = stars.map { it.findNumbersAdjacents(partNumbers)}.filter{it.size==2}.map{ it.first().valueAsInt * it.last().valueAsInt}
    fun pieceAt(x: Int, y: Int) = visualRepresentation[y][x]

    private fun String.findNumbers()  = "\\d+".toRegex().findAll(this)
    private fun String.findStars()  = "\\*".toRegex().findAll(this)
}

class Star(val value: String, val x: Int, val y: Int, engine: Engine){
    val coordinate = Coordinate(x,y,engine)

    fun findNumbersAdjacents(partNumbers: List<Number>) =
        partNumbers.filter { it.isAdjacentTo(coordinate) }
}

class Number(val value: String, xRange: IntRange, y: Int, engine: Engine) {
    val adjacentCoordinates = HorizontalRange(xRange, y, engine).adjacentCoordinates
    val adjacentPieces = adjacentCoordinates.map { it.piece}
    val isAdjacentToASymbol = adjacentPieces.any { it.isASymbol }
    val valueAsInt = value.toInt()

    fun isAdjacentTo(coordinate: Coordinate) = adjacentCoordinates.contains(coordinate)

    private val Char.isASymbol: Boolean
        get() = this != '.' && !this.isDigit()
    override fun toString() = value
}

data class Coordinate(val x: Int, val y: Int,val engine: Engine) {
    val isInEngine = x >=0 && y >= 0 && x <= engine.maxX && y <= engine.maxY
    val piece  by lazy {engine.pieceAt(x,y)}

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coordinate

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}

data class HorizontalRange(val x: IntRange, val y: Int, val  engine: Engine) {
    val adjacentsXRange = x.first - 1..x.last+1
    val adjacentCoordinates = (y-1..y+1).flatMap { y -> adjacentsXRange.map {x -> Coordinate(x,y,engine) } }
        .filter { it.isInEngine }
}
