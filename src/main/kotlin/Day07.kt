import kotlin.system.measureTimeMillis

enum class Type {
    FIVE_OF_KIND,
    FOUR_OF_KIND,
    FULL_HOUSE,
    THREE_OF_KIND,
    TWO_PAIR,
    ONE_PAIR,
    HIGH_CARD
}

data class Hand(val cards: String, val type: Type, val bid: Int)

fun main() {
//    val lines = getResourceAsLines("day7_test.txt")
    val lines = getResourceAsLines("day7.txt")

    val elapsedTime = measureTimeMillis {
//        day7First(lines)
        day7Second(lines)
    }

    println("Execution time: $elapsedTime milliseconds")
}

fun day7First(lines: List<String>) {
    val hands = getPairs(lines)
        .map { transformToHand(it.first, it.second, 1) }
        .sortedWith(::compareHands)
        .reversed()

    val result = hands.map { it.bid.toLong() }.mapIndexed { index, bid -> bid * (index + 1) }.sum()

    println("Total Winnings: $result")
}

fun day7Second(lines: List<String>) {
    val hands = getPairs(lines)
        .map { transformToHand(it.first, it.second, 2) }
        .sortedWith(::compareHandsPart2)
        .reversed()

    val result = hands.map { it.bid.toLong() }.mapIndexed { index, bid -> bid * (index + 1) }.sum()

    println("Total Winnings: $result")
}

fun getPairs(lines: List<String>): List<Pair<String, Int>> {
    return lines.map {
        val split = it.split(" ")
        Pair(split[0], split[1].toInt())
    }
}

fun transformToHand(cards: String, bid: Int, challengePart: Int): Hand {
    val type = if (challengePart == 1) {
        findHandType(cards)
    } else {
        findHandTypePart2(cards)
    }

    return Hand(cards, type, bid)
}

fun findHandType(hand: String): Type {
    val set = hand.toSet()
    val counts = set.map { c -> hand.count { it == c } }.sorted().reversed().joinToString("")

    when (set.size) {
        1 -> return Type.FIVE_OF_KIND
        2 -> {
            if (counts == "32") {
                return Type.FULL_HOUSE
            }
            if (counts == "41") {
                return Type.FOUR_OF_KIND
            }
        }

        3 -> {
            if (counts == "221") {
                return Type.TWO_PAIR
            }
            if (counts == "311") {
                return Type.THREE_OF_KIND
            }
        }

        4 -> return Type.ONE_PAIR
        5 -> return Type.HIGH_CARD
    }

    throw Exception("Hand $hand had no matches")
}

fun findHandTypePart2(hand: String): Type {
    val set = hand.toSet()
    val counts = set.map { c -> hand.count { it == c } }.sorted().reversed().joinToString("")

    if (set.contains('J')) {
        when (set.size) {
            1, 2 -> return Type.FIVE_OF_KIND
            3 -> {
                if (counts == "221") {
                    val jCount = hand.count { it == 'J' }
                    if(jCount == 1){
                        return Type.FULL_HOUSE
                    }
                    return Type.FOUR_OF_KIND
                }
                if (counts == "311") {
                    return Type.FOUR_OF_KIND
                }
            }

            4 -> return Type.THREE_OF_KIND
            5 -> return Type.ONE_PAIR
        }
    } else {
        when (set.size) {
            1 -> return Type.FIVE_OF_KIND
            2 -> {
                if (counts == "32") {
                    return Type.FULL_HOUSE
                }
                if (counts == "41") {
                    return Type.FOUR_OF_KIND
                }
            }

            3 -> {
                if (counts == "221") {
                    return Type.TWO_PAIR
                }
                if (counts == "311") {
                    return Type.THREE_OF_KIND
                }
            }

            4 -> return Type.ONE_PAIR
            5 -> return Type.HIGH_CARD
        }
    }

    throw Exception("Hand $hand had no matches")
}

fun compareHands(hand1: Hand, hand2: Hand): Int {
    val typeComparison = hand1.type.compareTo(hand2.type)

    if (typeComparison != 0) {
        return typeComparison
    } else {
        for (i in 0 until hand1.cards.length) {
            val card1 = hand1.cards[i]
            val card2 = hand2.cards[i]

            val comp = compareStrength(card1, card2, 1)
            if (comp == 0) {
                continue
            }

            return comp
        }

        return 0
    }
}

fun compareHandsPart2(hand1: Hand, hand2: Hand): Int {
    val typeComparison = hand1.type.compareTo(hand2.type)

    if (typeComparison != 0) {
        return typeComparison
    } else {
        for (i in 0 until hand1.cards.length) {
            val card1 = hand1.cards[i]
            val card2 = hand2.cards[i]

            val comp = compareStrength(card1, card2, 2)
            if (comp == 0) {
                continue
            }

            return comp
        }

        return 0
    }
}

fun compareStrength(card1: Char, card2: Char, challengePart: Int): Int {
    val strength = if (challengePart == 1) {
        listOf("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2")
    } else {
        listOf("A", "K", "Q", "T", "9", "8", "7", "6", "5", "4", "3", "2", "J")
    }

    return strength.indexOf(card1.toString()).compareTo(strength.indexOf(card2.toString()))
}
