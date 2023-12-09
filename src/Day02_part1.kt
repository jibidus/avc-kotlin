fun main() {
    fun answer(input: List<String>, red: Int, green: Int, blue: Int) = input.map { Game(it) }.filter {
        it.requiresLessThan(red, "red") && it.requiresLessThan(
            green,
            "green"
        ) && it.requiresLessThan(blue, "blue")
    }.sumOf { it.id }

    val testInputPart1 = readInput("Day02_part1_test")
    checkThat(answer(testInputPart1, 12, 13, 14)).isEqualTo(8)
    checkThat(answer(testInputPart1, 20, 13, 14)).isEqualTo(11)
    checkThat(answer(testInputPart1, 1, 1, 1)).isEqualTo(0)
    checkThat(answer(testInputPart1, 5, 30, 30)).isEqualTo(3)
    checkThat(answer(testInputPart1, 30, 30, 30)).isEqualTo(15)
    "Tests are successful!".println()

    val input = readInput("Day02")
    println(answer(input, 12, 13, 14))
}


// ex: "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
class Game(record: String) {
    private val parsed = record.substring("Game ".length).split(":")
    val id = parsed[0].toInt()
    val setsOfCubes = parsed[1].split(";").map { SetOfCubes(it) }

    fun requiresLessThan(maxNbOfCubes: Int, color: String): Boolean =
        setsOfCubes.all { it.requiresLessThan(maxNbOfCubes, color) }
}

class SetOfCubes(record: String) {
    private val parsed = record.trim().split(",")
    val cubes = parsed.map { Cubes(it) }
    fun requiresLessThan(maxNbOfCubes: Int, color: String): Boolean =
        cubes.filter { it.color == color }.all { it.nb <= maxNbOfCubes }
}

// ex: "3 blue"
class Cubes(record: String) {
    private val parsed = record.trim().split(" ")
    val nb = parsed[0].toInt()
    val color = parsed[1]

    override fun toString(): String {
        return "$nb $color"
    }
}
