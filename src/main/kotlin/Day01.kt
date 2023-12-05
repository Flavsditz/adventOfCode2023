import kotlin.system.measureTimeMillis

fun main() {
//    val lines = getResourceAsLines("day1_1_test.txt")
//    val lines = getResourceAsLines("day1_2_test.txt")
    val lines = getResourceAsLines("day1.txt")

    val elapsedTime = measureTimeMillis {
//        day1First(lines)
        day1Second(lines)
    }

    println("Execution time: $elapsedTime milliseconds")

}

fun day1First(lines: List<String>) {
    var sum = 0

    lines.forEach { line ->
        val matches = Regex("\\d").findAll(line)

        val digits = matches.map { it.value }.toList()

        sum += (digits.first() + digits.last()).toInt()
    }

    println("Total: $sum")
}

fun day1Second(lines: List<String>) {
    var sum = 0L

    val regex = Regex("one|two|three|four|five|six|seven|eight|nine|\\d")
    val regexReversed = Regex("eno|owt|eerht|ruof|evif|xis|neves|thgie|enin|\\d")

    lines.forEach{ line ->
        val matchReversed = regexReversed.findAll(line.lowercase().reversed())
        val matchListReversed = matchReversed.map { it.groupValues[0] }.toList().reversed()

        val match = regex.findAll(line.lowercase())
        val matchList = match.map { it.groupValues[0] }.toList()

        if (matchList.isEmpty()){
            throw Exception("Empty matches on line [$line]... should not happen")
        }

        val firstNumber = convertToInt(matchList.first())
        val lastNumber = convertToInt(matchListReversed.last())

        val stringVersion = ""+firstNumber+lastNumber

        sum += stringVersion.toLong()
    }

    println(sum.toString())
}

fun convertToInt(match: String): Int {
    if (match.first().isDigit()){
        return match.first().code - 48
    }

    return when(match){
        "one", "eno" -> 1
        "two", "owt" -> 2
        "three", "eerht" -> 3
        "four", "ruof" -> 4
        "five", "evif" -> 5
        "six", "xis" -> 6
        "seven", "neves" -> 7
        "eight", "thgie" -> 8
        "nine", "enin" -> 9
        else -> throw Exception("This should be a number, Got [$match] instead")
    }
}
