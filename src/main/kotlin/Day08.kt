import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {
//    val lines = getResourceAsLines("day8_1_test.txt")
//    val lines = getResourceAsLines("day8_2_test.txt")
//    val lines = getResourceAsLines("day8_3_test.txt")
    val lines = getResourceAsLines("day8.txt")

    val elapsedTime = measureTimeMillis {
//        day8First(lines)
        day8Second(lines)
    }

    println("Execution time: $elapsedTime milliseconds")
}

fun day8First(lines: List<String>) {
    val steps = getSteps(lines[0])
    val map = getNodeMap(lines.subList(2, lines.size))

    var counter = findStepsToZ("AAA", steps, map)

    println("We needed $counter steps")
}

private fun findStepsToZ(
    startNode: String,
    steps: List<String>,
    map: Map<String, Pair<String, String>>
): Int {
    var currentNode = startNode
    var counter = 0
    while (currentNode.last() != 'Z') {
        val step = steps[counter % steps.size]

        if (step == "L") {
            currentNode = map[currentNode]!!.first
        } else {
            currentNode = map[currentNode]!!.second
        }

        counter++
    }
    return counter
}

fun day8Second(lines: List<String>) {
    val steps = getSteps(lines[0])
    val map = getNodeMap(lines.subList(2, lines.size))

    val currentNodes = getAllEndingWithA(map)

    val minSteps = currentNodes.map { findStepsToZ(it, steps, map).toLong() }

    println("Total steps needed ${findLCM(minSteps)}")
}

fun getAllEndingWithA(map: Map<String, Pair<String, String>>): List<String> {
    return map.keys.filter { it.last() == 'A' }
}

fun getSteps(steps: String): List<String> {
    return steps.trim().map { it.toString() }.toList()
}

fun getNodeMap(lines: List<String>): Map<String, Pair<String, String>> {
    return lines.map {
        val (node, pairString) = it.split(" = ")
        val pairList = pairString.substring(1, pairString.length - 1).split(", ")

        node to Pair(pairList.first(), pairList.last())
    }.toMap()
}

fun gcd(a: Long, b: Long): Long {
    return if (b == 0L) abs(a) else gcd(b, a % b)
}

fun lcm(a: Long, b: Long): Long {
    return if (a == 0L || b == 0L) 0 else abs(a * b) / gcd(a, b)
}

fun findLCM(numbers: List<Long>): Long {
    require(numbers.isNotEmpty()) { "List must not be empty" }

    var result = numbers[0]

    for (i in 1 until numbers.size) {
        result = lcm(result, numbers[i])
    }

    return result
}