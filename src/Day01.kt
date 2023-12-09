fun main() {
    fun String.firstDigit() = find { it.isDigit() }

    fun String.lastDigit() = findLast { it.isDigit() }

    fun part1(input: List<String>): Int {
        return input.map { Pair(it.firstDigit(), it.lastDigit()) }.sumOf { "${it.first}${it.second}".toInt() }
    }

    val testInputPart1 = readInput("Day01_part1_test")
    checkThat(part1(testInputPart1)).isEqualTo(142)

    val otherDigits = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    fun String.findDigits(): Int {
        val foundDigits = mutableListOf<Int>()
        for (i in indices) {
            if (this[i].isDigit()) {
                foundDigits += this[i].digitToInt()
            }
            val entry = otherDigits.entries.firstOrNull { this.substring(i).startsWith(it.key) }
            if (entry != null) {
                foundDigits+=entry.value
            }
        }
        if (foundDigits.isEmpty()) {
            throw NoSuchElementException("No digit found in $this")
        }
        return "${foundDigits.first()}${foundDigits.last()}".toInt()
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { it.findDigits() }
    }

    val testInputPart2 = readInput("Day01_part2_test")
    checkThat(part2(testInputPart2)).isEqualTo(281)

    val input = readInput("Day01")
    "Answer are…".println()
    part1(input).println()
    part2(input).println()
}
