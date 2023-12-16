import kotlin.system.measureTimeMillis

data class Sequence(val label: String, val op: OP, val focalLength: Int = 0)

enum class OP { ADD, REMOVE }

fun main() {
//    val lines = getResourceAsLines("day15_test.txt")
    val lines = getResourceAsLines("day15.txt")

    val elapsedTime = measureTimeMillis {
//        day15First(lines)
        day15Second(lines)
    }

    println("Execution time: $elapsedTime milliseconds")
}

fun day15First(lines: List<String>) {
    val line = lines.first()
    val result = line.split(",").sumOf { hash(it) }

    println("Total $result")
}

private fun hash(line: String): Int {
    var currentVal = 0
    line.forEach {
        currentVal += it.code
        currentVal *= 17
        currentVal %= 256
    }
    return currentVal
}

fun day15Second(lines: List<String>) {
    val sequences = lines.first().split(",").map { parseSequence(it) }

    val boxes = HashMap<Int, MutableList<Sequence>>()
    for (i in 0..255) {
        boxes[i] = mutableListOf()
    }

    sequences.forEach { seq ->
        val boxNumber = hash(seq.label)
        val box = boxes[boxNumber]!!

        when (seq.op) {
            OP.ADD -> {
                val idx = box.indexOfFirst { it.label == seq.label }

                if (idx == -1) {
                    box.add(seq)
                } else {
                    box[idx] = seq
                }
            }

            OP.REMOVE -> {
                box.removeIf { it.label == seq.label }
            }
        }
    }

    val total = boxes.keys.map { k ->
        boxes[k]!!.mapIndexed { idx, s -> (k + 1) * (idx + 1) * s.focalLength }.sum()
    }.sum()

    println("Total $total")
}

fun parseSequence(s: String): Sequence {
    return if (s.contains('-')) {
        Sequence(s.split("-").first(), OP.REMOVE)
    } else {
        val split = s.split("=")
        Sequence(split.first(), OP.ADD, split.last().toInt())
    }
}
