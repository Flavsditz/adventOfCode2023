import kotlin.system.measureTimeMillis

fun main() {
//    val lines = getResourceAsLines("day6_test.txt")
    val lines = getResourceAsLines("day6.txt")

    val elapsedTime = measureTimeMillis {
//        day6First(lines)
        day6Second(lines)
    }

    println("Execution time: $elapsedTime milliseconds")
}

fun day6First(lines: List<String>) {
    val times = parseNumbers(lines[0])
    val distances = parseNumbers(lines[1])
    var waysOfWinning = MutableList(times.size) { 0 }

    times.forEachIndexed { idx, raceTime ->
        for (v in 1 until raceTime) {
            // The amount of time held will always be equal to the velocity
            val s = v * (raceTime - v)

            if (s > distances[idx]) {
                waysOfWinning[idx]++
            }
        }
    }

    val totalResult = waysOfWinning.reduce { acc, i -> acc * i }
    println("Result $totalResult")
}

fun day6Second(lines: List<String>) {
    val raceTime = parseSingleNumber(lines[0]).toLong()
    val recordDist = parseSingleNumber(lines[1]).toLong()

    var firstWinIdx = 0L
    var lastWinIdx = 0L

    var v = 1L
    while (firstWinIdx == 0L) {
        val s = v * (raceTime - v)

        if (s > recordDist) {
            firstWinIdx = v
        }
        v++
    }

    v = raceTime - 1L
    while (lastWinIdx == 0L) {
        val s = v * (raceTime - v)

        if (s > recordDist) {
            lastWinIdx = v
        }
        v--
    }

    val result = lastWinIdx - firstWinIdx + 1
    println("Result: $result")
}

fun parseNumbers(line: String): List<Int> {
    return Regex("\\d+").findAll(line).map { it.value.toInt() }.toList()
}

fun parseSingleNumber(line: String): String {
    return Regex("\\d+").findAll(line).map { it.value }.reduce { acc, s -> acc + s }
}

