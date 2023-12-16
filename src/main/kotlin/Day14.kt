import kotlin.system.measureTimeMillis

fun main() {
//    val lines = getResourceAsLines("day14_test.txt")
    val lines = getResourceAsLines("day14.txt")

    val elapsedTime = measureTimeMillis {
        day14First(lines)
//        day14Second(lines)
    }

    println("Execution time: $elapsedTime milliseconds")
}

fun day14First(lines: List<String>) {
    val tilted = tiltNorth(lines)

    val sum = tilted.reversed().mapIndexed { idx, l -> weightSum(l, idx + 1) }.sum()

    println("Total $sum")
}

fun weightSum(line: String, distToSouth: Int): Int {
    return line.count { it == 'O' } * distToSouth
}

fun tiltNorth(lines: List<String>): List<String> {
    val transposed = transposeCharacters(lines)

    val rolled = transposed.map { line ->
        var newLine = ""

        var rocksCount = 0
        var spaceSize = 0
        for (c in line) {
            when (c) {
                'O' -> {
                    rocksCount++
                }

                '.' -> spaceSize++
                '#' -> {
                    newLine += "O".repeat(rocksCount) + ".".repeat(spaceSize) + "#"
                    rocksCount = 0
                    spaceSize = 0
                }
            }
        }

        newLine += "O".repeat(rocksCount) + ".".repeat(spaceSize)

        newLine
    }

    return transposeCharacters(rolled)
}

fun transposeCharacters(strings: List<String>): List<String> {
    val maxLength = strings.maxByOrNull { it.length }?.length ?: 0

    return List(maxLength) { index ->
        strings.filter { it.length > index }
            .map { it[index] }
            .joinToString("")
    }
}


fun day14Second(lines: List<String>) {

    println("Total ")
}
