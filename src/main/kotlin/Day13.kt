import kotlin.system.measureTimeMillis

fun main() {
//    val lines = getResourceAsLines("day13_test.txt")
    val lines = getResourceAsLines("day13.txt")

    val elapsedTime = measureTimeMillis {
        day13First(lines)
//        day13Second(lines)
    }

    println("Execution time: $elapsedTime milliseconds")
}

fun day13First(lines: List<String>) {
    val blocks = blockify(lines)

    var sum = 0

    for (block in blocks) {

        var vDivs = possibleVerticalDiv(block[0])

        for (line in block.subList(1, block.size)) {
            vDivs = possibleVerticalDiv(line, vDivs)

            if (vDivs.isEmpty()) {
                break
            }
        }

        if (vDivs.size == 1) {
            sum += vDivs.first() + 1
            continue
        }

        val hDiv = horizontalDiv(block)
        sum += 100 * hDiv
    }

    println("Total $sum")
}

fun blockify(lines: List<String>): List<List<String>> {
    val blocks = mutableListOf<List<String>>()

    var block = mutableListOf<String>()
    for (line in lines) {
        if (line.isEmpty()) {
            blocks.add(block)
            block = mutableListOf()
        } else {
            block.add(line)
        }
    }

    blocks.add(block)

    return blocks
}

fun horizontalDiv(lines: List<String>): Int {
    val halfIdx = lines.size / 2
    for (idx in 0..<lines.lastIndex) {
        val s = lines[idx]
        if (s == lines[idx + 1]) {

            val groups = if (idx >= halfIdx) {
                val bottomSide = lines.subList(idx + 1, lines.lastIndex).reversed()
                lines.subList(idx - bottomSide.lastIndex, idx + 1) to bottomSide
            } else {
                val topSide = lines.subList(0, idx + 1)
                topSide to lines.subList(idx + 1, idx + 1 + topSide.size).reversed()
            }

            if (groups.first == groups.second) {
                return idx + 1
            }
        }
    }

    return 0
}

fun possibleVerticalDiv(line: String, vDivs: List<Int> = emptyList()): List<Int> {
    val possiblePoints = mutableListOf<Int>()

    val range = if (vDivs.isEmpty()) {
        0..<line.lastIndex
    } else {
        vDivs
    }

    val halfIdx = line.length / 2
    for (idx in range) {
        val c = line[idx]
        if (c == line[idx + 1]) {
            // Possible match. Keep testing

            //if after middle, then we shorten left otherwise right
            val points = if (idx >= halfIdx) {
                val rightSide = line.substring(idx + 1).reversed()
                line.substring(idx - rightSide.lastIndex, idx + 1) to rightSide
            } else {
                val leftSide = line.substring(0, idx + 1)
                leftSide to line.substring(idx + 1, idx + 1 + leftSide.length).reversed()
            }

            if (points.first == points.second) {
                possiblePoints.add(idx)
            }

        }
    }

    return possiblePoints
}


fun day13Second(lines: List<String>) {

    println("Total ")
}
