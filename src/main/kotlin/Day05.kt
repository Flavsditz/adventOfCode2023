import kotlin.system.measureTimeMillis

data class Almanac(val sourceRange: LongRange, val destinationRange: LongRange)

fun main() {
//    val lines = getResourceAsLines("day5_test.txt")
    val lines = getResourceAsLines("day5.txt")

    val elapsedTime = measureTimeMillis {
        day5First(lines)
//        day5Second(lines)
    }

    println("Execution time: $elapsedTime milliseconds")
}

fun day5First(lines: List<String>) {
    val seeds = getSeeds(lines[0])
    val maps = generateMappings(lines)

    val min = findMinLocation(seeds, maps)

    println("Smallest location: $min")
}

fun day5Second(lines: List<String>) {
    val maps = generateMappings(lines)

    val seedRanges = getSeedRanges(lines[0])

    val intervals = seedRanges.map { chunkRange(it, 10000L) }.flatten()
    println("There are ${intervals.size} intervals")

    val min = intervals
        .parallelStream()
        .mapToLong {
            findMinLocation(it.toList(), maps)
        }
        .min()

    println("\nSmallest Location: ${min.asLong}")
}

private fun generateMappings(lines: List<String>): MutableList<MutableList<Almanac>> {
    val maps = mutableListOf<MutableList<Almanac>>()

    var tmpMap = mutableListOf<Almanac>()
    lines.subList(1, lines.lastIndex).filterNot { it.isEmpty() }.forEach {
        if (it.contains(" map:")) {
            if (tmpMap.isNotEmpty()) {
                maps.add(tmpMap)
                tmpMap = mutableListOf<Almanac>()
            }
        } else {
            val (destRange, sourceRange) = getRanges(it)

            tmpMap.add(Almanac(sourceRange, destRange))
        }
    }
    return maps
}

fun getSeedRanges(line: String): List<LongRange> {
    val seedAndRange = line.split("seeds: ")[1].trim().split(" ").map { it.toLong() }.chunked(2)

    return seedAndRange.map { it[0]..<it[0] + it[1] }.sortedBy { it.first }
}

fun chunkRange(originalRange: LongRange, chunkSize: Long): MutableList<LongRange> {
    val chunks = mutableListOf<LongRange>()

    var start = originalRange.start
    while (start <= originalRange.endInclusive) {
        val end = (start + chunkSize - 1).coerceAtMost(originalRange.endInclusive)
        chunks.add(start..end)
        start += chunkSize
    }

    return chunks
}

fun findMinLocation(seeds: List<Long>, maps: MutableList<MutableList<Almanac>>): Long {

    val sources = seeds.toMutableList()
    var tmp = seeds.toMutableList()

    maps.forEach { ranges ->
        ranges.forEach { entry ->
            sources.forEachIndexed { index, l ->
                if (entry.sourceRange.contains(l)) {
                    val sourceIdx = binarySearch(entry.sourceRange, l)
                    val destVal = entry.destinationRange.first() + sourceIdx

                    tmp[index] = destVal
                }
            }
        }

        tmp.forEachIndexed { index, l ->
            if (l != -1L) {
                sources[index] = l
            }
        }
        tmp = MutableList(seeds.size) { -1L }
    }

    return sources.min()
}

fun binarySearch(range: LongRange, target: Long): Long {
    var low = 0L
    var high = range.last - range.first

    while (low <= high) {
        val mid = low + (high - low) / 2
        val midValue = range.first + mid

        when {
            midValue == target -> return mid // Element found
            midValue < target -> low = mid + 1 // Search in the right half
            else -> high = mid - 1 // Search in the left half
        }
    }

    return -1L // Element not found
}

fun getSeeds(line: String): List<Long> {
    return line.split("seeds: ")[1].trim().split(" ").map { it.toLong() }
}

fun getRanges(line: String): Pair<LongRange, LongRange> {
    val tmp = line.trim().split(" ").map { it.toLong() }

    if (tmp.size != 3) {
        throw Exception("Mapping has incorrect size (should be 3)")
    }

    return Pair(tmp[0]..<tmp[0] + tmp[2], tmp[1]..<tmp[1] + tmp[2])
}


