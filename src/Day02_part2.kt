fun main() {
    fun answer(input: List<String>) = input.map { Game(it) }.sumOf { it.power }

    checkThat(Game("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green").power).isEqualTo(48)
    checkThat(Game("Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue").power).isEqualTo(12)
    val testInputPart1 = readInput("Day02_test")
    checkThat(answer(testInputPart1)).isEqualTo(2286)
    "Tests are successful!".println()

    val input = readInput("Day02")
    println(answer(input))
}