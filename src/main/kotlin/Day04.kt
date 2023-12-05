import kotlin.math.pow
import kotlin.system.measureTimeMillis

fun main() {
//    val lines = getResourceAsLines("day4_test.txt")
    val lines = getResourceAsLines("day4.txt")

    val elapsedTime = measureTimeMillis {
//        day4First(lines)
        day4Second(lines)
    }

    println("Execution time: $elapsedTime milliseconds")
}

fun day4First(lines: List<String>) {
    var sum = 0

    lines.forEachIndexed { lineIndex, line ->
        val matches = Regex("(Card [ \\d]+:)([ \\d]+)(\\|)([ \\d]+)").findAll(line)

        val (_, winList, _, haveList) = matches.first().destructured

        val winNumbers = winList.trim().split(Regex("\\s+")).map { it.toInt() }
        val haveNumbers = haveList.trim().split(Regex("\\s+")).map { it.toInt() }

        val total = haveNumbers.filter { winNumbers.contains(it) }.count()

        if (total > 0) {
            sum += 2.0.pow(total - 1).toInt()
        }
    }

    println("Total: $sum")
}

fun day4Second(lines: List<String>) {
    val cardCount = MutableList(lines.size) { 1 }

    lines.forEachIndexed { lineIndex, line ->
        val matches = Regex("(Card [ \\d]+:)([ \\d]+)(\\|)([ \\d]+)").findAll(line)

        val (_, winList, _, haveList) = matches.first().destructured

        val winNumbers = winList.trim().split(Regex("\\s+")).map { it.toInt() }
        val haveNumbers = haveList.trim().split(Regex("\\s+")).map { it.toInt() }

        val total = haveNumbers.filter { winNumbers.contains(it) }.count()

        for (numOfCards in 0 until cardCount[lineIndex]) {
            for (i in lineIndex + 1..lineIndex + total) {
                if (i < cardCount.size) {
                    cardCount[i]++
                }
            }
        }
    }

    val sum = cardCount.sum()
    println("Total: $sum")
}


