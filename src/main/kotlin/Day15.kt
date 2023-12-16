import kotlin.system.measureTimeMillis

fun main() {
//    val lines = getResourceAsLines("day15_test.txt")
    val lines = getResourceAsLines("day15.txt")

    val elapsedTime = measureTimeMillis {
        day15First(lines)
//        day15Second(lines)
    }

    println("Execution time: $elapsedTime milliseconds")
}

fun day15First(lines: List<String>) {
    val line = lines.first()
    val result = line.split(",")
        .map { hash(it) }
        .sum()

    println("Total $result")
}

private fun hash(line: String): Long {
    var currentVal = 0L
    line.forEach {
        currentVal += it.code
        currentVal *= 17
        currentVal %= 256
    }
    return currentVal
}

fun day15Second(lines: List<String>) {

    println("Total ")
}
