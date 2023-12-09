fun main() {
    fun part1(input: List<String>): Int {
        return input.map { Pair(it.firstDigit(), it.lastDigit()) }.sumOf { "${it.first}${it.second}".toInt() }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    "Answer is…".println()
    part1(input).println()
    part2(input).println()
}
