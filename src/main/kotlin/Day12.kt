import java.lang.Exception
import kotlin.system.measureTimeMillis

data class Records(val conditions: String, val brokenGroups: List<Int>)

fun main() {
    val lines = getResourceAsLines("day12_test.txt")
//    val lines = getResourceAsLines("day12.txt")

    val elapsedTime = measureTimeMillis {
        day12First(lines)
//        day12Second(lines)
    }

    println("Execution time: $elapsedTime milliseconds")
}

//fun day12First(lines: List<String>) {
//    val records = getRecords(lines)
//    var sum = 0L
//    for (r in records) {
//        val g = generateLineArrangements(r.conditions.length, r.brokenGroups)
//        val valids = g.filter { isValidPossibility(it, r.conditions) }.toList()
//
//        val l = valids.count().toLong()
////        println(l)
////        valids.forEach { println("    $it") }
//        sum += l
//    }
//    println("Total $sum")
//}

fun day12First(lines: List<String>) {
    val records = getRecords(lines)
    var sum = 0L
    for (r in records) {
        val g = generateLineArrangements(r.conditions, r.brokenGroups)
        val valids = g//.filter { isValidPossibility(it, r.conditions) }.toList()

        val l = valids.count().toLong()
        println(l)
        valids.forEach { println("    $it") }
        sum += l
    }
    println("Total $sum")
}

fun getRecords(lines: List<String>): List<Records> {
    val regex = Regex("([?#.]+) ([\\d+,]+)")
    return lines.map {
        val matches = regex.find(it)!!
        matches.groupValues
    }.map { Records(it[1], it[2].split(",").map { it.toInt() }) }
}

fun generateLineArrangements(size: Int, groupSizes: List<Int>): List<String> {
    val arrangements = mutableListOf<String>()
    val sumOfGroups = groupSizes.sum()

    val subList = groupSizes.subList(1, groupSizes.size)
    for (i in 0..size - sumOfGroups) {
        var s = ".".repeat(i) + "#".repeat(groupSizes[0])

        if (s.length < size) {
            s += "."
            val rest = size - s.length

            if (subList.isNotEmpty()) {
                val subPossibilities = generateLineArrangements(rest, subList)

                val map = subPossibilities.map { s + it }
                arrangements.addAll(map)
            } else {
                s += ".".repeat(rest)
                arrangements.add(s)
            }
        } else {
            arrangements.add(s)
            break
        }

    }



    return arrangements.toList()
}

fun generateLineArrangements(refString: String, groupSizes: List<Int>): List<String> {
    if (!groupsMatch(refString.split("?")[0], groupSizes)) {
        return emptyList()
    }
    if (!refString.contains("?")) {
        return listOf(refString)
    }

    val arrangements = mutableListOf<String>()

    //get first ?
    var newString = refString.replaceFirst('?', '.')

    // call rest recursively
    arrangements.addAll(generateLineArrangements(newString, groupSizes))

    // substitute by '#'
    newString = refString.replaceFirst('?', '#')

    // call rest recursively
    arrangements.addAll(generateLineArrangements(newString, groupSizes))

    //see if group count match
    return arrangements.filter { groupsMatch(it, groupSizes) }
}

fun groupsMatch(s: String, groups: List<Int>): Boolean {
    val r = Regex("[#]+").findAll(s)

    val lens = r.map { it.value.length }.toList()

    lens.forEachIndexed { idx, i ->
        if (i != groups[idx]) {
            return false
        }
    }
    return true
}

fun isValidPossibility(possibility: String, currentState: String): Boolean {
    return currentState.mapIndexed { index, c ->
        when (c) {
            '?' -> 0
            '.' -> if (possibility[index] == '.') 0 else 1
            '#' -> if (possibility[index] == '#') 0 else 1
            else -> throw Exception("Symbol unrecognized in $currentState")
        }
    }.sum() == 0
}

fun day12Second(lines: List<String>) {
    val records = getRecords(lines)
    val unfoldRecords = records.map { r ->
        val newCond = List(5) { r.conditions }.joinToString("?")

        val newGroups = List(5) { r.brokenGroups }.flatten()

        Records(newCond, newGroups)
    }

    var sum = 0L

//    unfoldRecords.parallelStream().map {  }

    for (r in unfoldRecords) {
        val g = generateLineArrangements(r.conditions.length, r.brokenGroups)

        sum += g.count().toLong()
    }
    println("Total $sum")
}
