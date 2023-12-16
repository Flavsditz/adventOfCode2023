import kotlin.system.measureTimeMillis

fun main() {
    val lines = getResourceAsLines("day14_test.txt")
//    val lines = getResourceAsLines("day14.txt")

    val elapsedTime = measureTimeMillis {
//        day14First(lines)
        day14Second(lines)
    }

    println("Execution time: $elapsedTime milliseconds")
}

fun day14First(lines: List<String>) {
    val tilted = tiltNorth(lines)

    val sum = tilted.reversed().mapIndexed { idx, l -> weightSum(l, idx + 1) }.sum()

    println("Total $sum")
}

fun tiltNorth(lines: List<String>): List<String> {
    val transposed = transposeCharacters(lines)

    val rolled = rollRocks(transposed)

    return transposeCharacters(rolled)
}

fun tiltWest(lines: List<String>): List<String> {
    return rollRocks(lines)
}

fun tiltSouth(lines: List<String>): List<String> {
    val transposed = transposeCharacters(lines)

    val rolled = rollRocks(transposed, true)

    return transposeCharacters(rolled)
}

fun tiltEast(lines: List<String>): List<String> {
    return rollRocks(lines, true)
}


private fun rollRocks(rows: List<String>, reverse: Boolean = false): List<String> {
    val rolled = rows.map { line ->
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
                    newLine += if (reverse) {
                        ".".repeat(spaceSize) + "O".repeat(rocksCount) + "#"
                    } else {
                        "O".repeat(rocksCount) + ".".repeat(spaceSize) + "#"
                    }
                    rocksCount = 0
                    spaceSize = 0
                }
            }
        }

        newLine += if (reverse) {
            ".".repeat(spaceSize) + "O".repeat(rocksCount)
        } else {
            "O".repeat(rocksCount) + ".".repeat(spaceSize)
        }

        newLine
    }
    return rolled
}

fun weightSum(line: String, distToSouth: Int): Int {
    return line.count { it == 'O' } * distToSouth
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
//    var tmp = lines
//    for (i in 1..200) {
//        tmp = spinCycle(tmp)
//        val sum = tmp.reversed().mapIndexed { idx, l -> weightSum(l, idx + 1) }.sum()
//        println("$sum")
//    }

    val pattern = listOf(
        90197,
        90212,
        90217,
        90213,
        90200,
        90188,
        90177,
        90176,
        90182,
        90198,
        90211,
        90218,
        90212,
        90201,
        90187,
        90178,
        90175,
        90183
    )

    val amountBeforePattern = 93
    val searchNumber = 1000000000
    val idx = (searchNumber - amountBeforePattern) % pattern.size


//    val pattern = listOf(
//        69,
//        69,
//        65,
//        64,
//        65,
//        63,
//        68
//    )
//
//    val amountBeforePattern = 3
//    val searchNumber = 1000000000
//    val idx = (searchNumber - amountBeforePattern) % pattern.size
    println("Total ${pattern[idx]}")
}

fun spinCycle(lines: List<String>): List<String> {
//    lines.forEach { println(it) }
    var tmp = tiltNorth(lines)
//    println("\n Tilted North:")
//    tmp.forEach { println(it) }
    tmp = tiltWest(tmp)
//    println("\n Tilted West:")
//    tmp.forEach { println(it) }
    tmp = tiltSouth(tmp)
//    println("\n Tilted South:")
//    tmp.forEach { println(it) }
    tmp = tiltEast(tmp)
//    println("\n Tilted East:")
//    tmp.forEach { println(it) }
    return tmp
}
