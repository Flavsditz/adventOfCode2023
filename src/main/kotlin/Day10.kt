import kotlin.math.abs
import kotlin.system.measureTimeMillis

data class Movement(val horizontal: Int, val vertical: Int, val direction: Direction)
class Mover(var currCol: Int, var currRow: Int, var steps: Int = 0, var lastMove: Movement?) {
    fun sameLocation(moverB: Mover): Boolean {
        return currCol == moverB.currCol && currRow == moverB.currRow
    }

    fun getCurrentPipe(lines: List<String>): Char {
        return lines[currRow][currCol]
    }

    fun move(nextMove: Movement) {
        currCol += nextMove.horizontal
        currRow += nextMove.vertical
        steps++

        lastMove = nextMove
    }
}

data class Node(val x: Int, val y: Int)
enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

fun main() {
//    val lines = getResourceAsLines("day10_test.txt")
    val lines = getResourceAsLines("day10.txt")

    val elapsedTime = measureTimeMillis {
//        day10First(lines)
        day10Second(lines)
    }

    println("Execution time: $elapsedTime milliseconds")
}

fun day10First(lines: List<String>) {
    val (startColumn, startRow) = getStartPosition(lines)

    //Find 2 start directions
    val mover1 = Mover(startColumn, startRow, 0, null)
    val mover2 = Mover(startColumn, startRow, 0, null)

    val startingMoves = getStartingMovements(startRow, startColumn, lines)
    mover1.move(startingMoves.first)
    mover2.move(startingMoves.second)

    while (!mover1.sameLocation(mover2)) {
        // Move mover1
        val next1 = getMovement(
            mover1.getCurrentPipe(lines),
            mover1.lastMove!!.direction
        )

        mover1.move(next1)

        // Move mover2
        val next2 = getMovement(
            mover2.getCurrentPipe(lines),
            mover2.lastMove!!.direction
        )

        mover2.move(next2)
    }


    println("Total steps ${mover1.steps}")
}

private fun getStartPosition(lines: List<String>): Pair<Int, Int> {
    var startColumn = 0
    var startRow = 0
    for (line in lines) {
        val idx = line.indexOf('S')
        if (idx != -1) {
            startColumn = idx
            break
        }
        startRow++
    }
    return Pair(startColumn, startRow)
}

fun getStartingMovements(startRow: Int, startColumn: Int, lines: List<String>): Pair<Movement, Movement> {
    val list = mutableListOf<Movement>()

    if (startRow > 0 && canMove(lines[startRow - 1][startColumn], Direction.UP)) {
        list.add(Movement(0, -1, Direction.UP))
    }

    if (startRow < lines.lastIndex && canMove(lines[startRow + 1][startColumn], Direction.DOWN)) {
        list.add(Movement(0, 1, Direction.DOWN))
    }

    if (startColumn > 0 && canMove(lines[startRow][startColumn - 1], Direction.LEFT)) {
        list.add(Movement(-1, 0, Direction.LEFT))
    }

    if (startColumn < lines[0].lastIndex && canMove(lines[startRow][startColumn + 1], Direction.RIGHT)) {
        list.add(Movement(1, 0, Direction.RIGHT))
    }

    return list.first() to list.last()
}

fun canMove(pipe: Char, direction: Direction): Boolean {
    return when (direction) {
        Direction.UP -> "|7F".contains(pipe)
        Direction.DOWN -> "|LJ".contains(pipe)
        Direction.LEFT -> "-LF".contains(pipe)
        Direction.RIGHT -> "-J7".contains(pipe)
    }
}

fun getMovement(pipe: Char, direction: Direction): Movement {
    return when (direction) {
        Direction.UP -> {
            when (pipe) {
                '|' -> Movement(0, -1, Direction.UP)
                '7' -> Movement(-1, 0, Direction.LEFT)
                'F' -> Movement(1, 0, Direction.RIGHT)
                else -> throw Exception("Not a valid Pipe: $pipe, ${direction.name}")
            }
        }

        Direction.DOWN -> {
            when (pipe) {
                '|' -> Movement(0, 1, Direction.DOWN)
                'L' -> Movement(1, 0, Direction.RIGHT)
                'J' -> Movement(-1, 0, Direction.LEFT)
                else -> throw Exception("Not a valid Pipe: $pipe, ${direction.name}")
            }
        }

        Direction.LEFT -> {
            when (pipe) {
                '-' -> Movement(-1, 0, Direction.LEFT)
                'L' -> Movement(0, -1, Direction.UP)
                'F' -> Movement(0, 1, Direction.DOWN)
                else -> throw Exception("Not a valid Pipe: $pipe, ${direction.name}")
            }
        }

        Direction.RIGHT -> {
            when (pipe) {
                '-' -> Movement(1, 0, Direction.RIGHT)
                'J' -> Movement(0, -1, Direction.UP)
                '7' -> Movement(0, 1, Direction.DOWN)
                else -> throw Exception("Not a valid Pipe: $pipe, ${direction.name}")
            }
        }
    }
}

fun day10Second(lines: List<String>) {
    val (startColumn, startRow) = getStartPosition(lines)

    val mover = Mover(startColumn, startRow, 0, null)
    val startingMoves = getStartingMovements(startRow, startColumn, lines)

    //Favour Clockwise (for Shoelace Theorem calculation)
    if (listOf(Direction.UP, Direction.RIGHT).contains(startingMoves.first.direction)) {
        mover.move(startingMoves.first)
    } else {
        mover.move(startingMoves.second)
    }

    val visitedNodes = mutableListOf(Node(mover.currRow, mover.currCol))

    while (mover.currCol != startColumn || mover.currRow != startRow) {
        val next1 = getMovement(
            mover.getCurrentPipe(lines),
            mover.lastMove!!.direction
        )

        mover.move(next1)
        visitedNodes.add(Node(mover.currRow, mover.currCol))
    }

    val boundaryPoints = mover.steps
    val area = calculateArea(visitedNodes)

    val interiorNodes = area - boundaryPoints/2 +1

    println("Interior Nodes: $interiorNodes")
}

fun calculateArea(visitedNodes: MutableList<Node>): Int {
    val leftSide = visitedNodes.mapIndexed { idx, node ->
        node.x * visitedNodes[(idx + 1) % visitedNodes.size].y
    }.sum()

    val rightSide = visitedNodes.mapIndexed { idx, node ->
        node.y * visitedNodes[(idx + 1) % visitedNodes.size].x
    }.sum()

    return abs(leftSide - rightSide) / 2
}
