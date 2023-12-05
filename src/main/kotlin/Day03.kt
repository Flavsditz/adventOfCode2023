import kotlin.system.measureTimeMillis

fun main() {
    //    val lines = getResourceAsLines("day3_test.txt")
    val lines = getResourceAsLines("day3.txt")

    val elapsedTime = measureTimeMillis {
//        day3First(lines)
        day3Second(lines)
    }

    println("Execution time: $elapsedTime milliseconds")

}

fun day3First(lines: List<String>) {
    var sum = 0

    lines.forEachIndexed { lineIndex, line ->
        val matches = Regex("[@*\$&/=\\-+#%]").findAll(line)

        matches.forEach {
            // Always one (idx..idx)
            val symbolLocation = it.range.first

            // Check top line
            if (lineIndex > 0) {
                sum += check(lines[lineIndex - 1], symbolLocation).first.sum()
            }
            // Check this line
            sum += check(lines[lineIndex], symbolLocation).first.sum()
            // Check bottom line
            if (lineIndex < lines.size) {
                sum += check(lines[lineIndex + 1], symbolLocation).first.sum()
            }

        }

    }

    println("Total: $sum")
}

fun day3Second(lines: List<String>) {
    var sum = 0L

    lines.forEachIndexed { lineIndex, line ->
        val matches = Regex("[*]").findAll(line)

        matches.forEach {
            // Always one (idx..idx)
            val symbolLocation = it.range.first

            var res: Pair<List<Int>, Int>
            val tempNums = mutableListOf<Int>()
            var adjCount = 0
            // Check top line
            if (lineIndex > 0) {
                res = check(lines[lineIndex - 1], symbolLocation)
                if (res.second != 0) {
                    tempNums.addAll(res.first)
                }
                adjCount += res.second
            }

            // Check this line
            res = check(lines[lineIndex], symbolLocation)
            if (res.second != 0) {
                tempNums.addAll(res.first)
            }
            adjCount += res.second

            // Check bottom line
            if (lineIndex < lines.size) {
                res = check(lines[lineIndex + 1], symbolLocation)
                if (res.second != 0) {
                    tempNums.addAll(res.first)
                }
                adjCount += res.second
            }

            if (adjCount == 2) {
                if (tempNums.size != 2) {
                    throw Exception("Line $lineIndex not matching ")
                }
                sum += tempNums.first() * tempNums.last()
            }

        }

    }

    println("Total: $sum")
}

fun check(line: String, index: Int): Pair<List<Int>, Int> {
    val matches = Regex("(\\d+)").findAll(line)

    val mapped = matches
        .filter { it.range.contains(index - 1) || it.range.contains(index) || it.range.contains(index + 1) }
        .map { it.value.toInt() }.toList()

    return Pair(mapped, mapped.size)
}



