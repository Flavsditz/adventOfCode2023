import kotlin.system.measureTimeMillis

class Galaxy(val row: Int, val col: Int)

fun main() {
//    val lines = getResourceAsLines("day11_test.txt")
    val lines = getResourceAsLines("day11.txt")

    val elapsedTime = measureTimeMillis {
//        day11First(lines)
        day11Second(lines)
    }

    println("Execution time: $elapsedTime milliseconds")
}

fun day11First(lines: List<String>) {
    val galaxies = getGalaxies(lines)
    val (missingRows, missingCols) = findEmptyPartsOfGalaxy(galaxies, lines)
    val pairs = pairOfGalaxies(galaxies)

    val total = pairs.sumOf {
        calculateDistanceWithExpandedGalaxy(it.first.col, it.second.col, missingCols) +
                calculateDistanceWithExpandedGalaxy(it.first.row, it.second.row, missingRows)
    }

    println("Total ${total}")
}

fun findEmptyPartsOfGalaxy(galaxies: List<Galaxy>, lines: List<String>): Pair<List<Int>, List<Int>> {
    val rows = galaxies.map { it.row }.toSet()
    val cols = galaxies.map { it.col }.toSet()

    val missingRows = (0..lines[0].lastIndex).filter { !rows.contains(it) }
    val missingCols = lines.indices.filter { !cols.contains(it) }.reversed()

    return missingRows to missingCols
}

private fun getGalaxies(lines: List<String>) = lines.flatMapIndexed { rowIndex, line ->
    line.mapIndexedNotNull { colIndex, char ->
        if (char == '#') Galaxy(rowIndex, colIndex) else null
    }
}

fun pairOfGalaxies(galaxies: List<Galaxy>): List<Pair<Galaxy, Galaxy>> {
    val pairs = mutableListOf<Pair<Galaxy, Galaxy>>()

    for (i in galaxies.indices) {
        for (j in i + 1..<galaxies.size) {
            pairs.add(Pair(galaxies[i], galaxies[j]))
        }
    }

    return pairs
}

fun calculateDistanceWithExpandedGalaxy(
    pointA: Int,
    pointB: Int,
    emptyPoints: List<Int>,
    expansionFactor: Long = 1L
): Long {
    val min = minOf(pointA, pointB)
    val max = maxOf(pointA, pointB)

    val range = (min..max)

    val extraDist = emptyPoints.count { range.contains(it) } * expansionFactor

    return max - min + extraDist
}

fun day11Second(lines: List<String>) {
    val galaxies = getGalaxies(lines)
    val (missingRows, missingCols) = findEmptyPartsOfGalaxy(galaxies, lines)
    val pairs = pairOfGalaxies(galaxies)

    val expansionFactor = 999999L // Rows ADDED beside original
    val total = pairs.sumOf {
        calculateDistanceWithExpandedGalaxy(it.first.col, it.second.col, missingCols, expansionFactor) +
                calculateDistanceWithExpandedGalaxy(it.first.row, it.second.row, missingRows, expansionFactor)
    }

    println("Total $total")
}