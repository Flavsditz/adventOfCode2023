import java.util.concurrent.atomic.AtomicLong
import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {
//    val lines = getResourceAsLines("day9_test.txt")
    val lines = getResourceAsLines("day9.txt")

    val elapsedTime = measureTimeMillis {
//        day9First(lines)
        day9Second(lines)
    }

    println("Execution time: $elapsedTime milliseconds")
}

fun day9First(lines: List<String>) {
    val sum = AtomicLong(0L)

    lines
        .parallelStream()
        .map { asNumberList(it) }
        .forEach { nums ->
            val results = mutableListOf<Long>()
            var reduced = nums
            results.add(reduced.last())
            while (true) {
                reduced = reduced
                    .subList(0, reduced.lastIndex)
                    .mapIndexed { index, l ->
                        reduced[index + 1] - l
                    }

                results.add(reduced.last())

                // Sanity check
                if (reduced.last() == 0L && reduced.first() == 0L) {
                    sum.getAndAdd(results.sum())
//                    println("Intermediate Result: ${results.sum()}")
                    break
                }
            }

        }

    println("Total sum: $sum")
}

fun asNumberList(s: String): List<Long> {
    return s.split(" ").map { it.toLong() }
}

fun day9Second(lines: List<String>) {
    val sum = AtomicLong(0L)

    lines
        .parallelStream()
        .map { asNumberList(it) }
        .forEach { nums ->
            val results = mutableListOf<Long>()
            var reduced = nums
            results.add(reduced.first())
            while (true) {
                reduced = reduced
                    .subList(0, reduced.lastIndex)
                    .mapIndexed { index, l ->
                        reduced[index + 1] - l
                    }

                results.add(reduced.first())

                // Sanity check
                if (reduced.last() == 0L && reduced.first() == 0L) {
                    sum.getAndAdd(results.reversed().reduce { acc, l -> l - acc })
//                    println("Intermediate Result: ${results.reversed().reduce { acc, l -> l - acc }}")
                    break
                }
            }

        }

    println("Total sum: $sum")
}