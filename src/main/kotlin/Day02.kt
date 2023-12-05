import kotlin.system.measureTimeMillis

data class RGB(val red: Int, val green: Int, val blue: Int)

fun main() {
//    val lines = getResourceAsLines("day2_test.txt")
    val lines = getResourceAsLines("day2.txt")

    val elapsedTime = measureTimeMillis {
//        day2First(lines)
        day2Second(lines)
    }

    println("Execution time: $elapsedTime milliseconds")

}

fun day2First(lines: List<String>) {
    var sum = 0

    lines.forEach { line ->
        val gameNumber = getGameNumber(line)

        val (maxRed, maxGreen, maxBlue) = getMaxCubesCount(line)

        if (maxRed <= 12 && maxGreen <= 13 && maxBlue <= 14) {
            sum += gameNumber
        }
    }

    println("Total: $sum")
}

fun day2Second(lines: List<String>) {
    var sum = 0

    lines.forEach { line ->
        val (maxRed, maxGreen, maxBlue) = getMaxCubesCount(line)

        val power = maxRed * maxGreen * maxBlue

        sum += power

//        println("Game $gameNumber needs:  $maxRed reds, $maxGreen greens and $maxBlue blues = power $power")
    }

    println("Total: $sum")
}

private fun getMaxCubesCount(line: String): Triple<Int, Int, Int> {
    val sets = line.split(":")[1].split(";")

    val rgbList = sets.map {
        val reds = getColorInSet(it, "red")
        val greens = getColorInSet(it, "green")
        val blues = getColorInSet(it, "blue")

        RGB(reds, greens, blues)
    }.toList()

    val maxRed = rgbList.maxOf { it.red }
    val maxGreen = rgbList.maxOf { it.green }
    val maxBlue = rgbList.maxOf { it.blue }
    return Triple(maxRed, maxGreen, maxBlue)
}

fun getGameNumber(line: String): Int {
    val gameNumberRegex = Regex("(Game) (\\d+)")
    val matches = gameNumberRegex.find(line)!!

    val (_, gameNumber) = matches.destructured

    return gameNumber.toInt()
}

fun getColorInSet(set: String, color: String): Int {
    val cubeRegex = Regex("(\\d+) ($color)")
    val cubeMatches = cubeRegex.find(set) ?: return 0

    val (amount, _) = cubeMatches.destructured

    return amount.toInt()
}