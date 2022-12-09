import Utils.Companion.readFromResources
import org.junit.jupiter.api.Test
import kotlin.math.abs

class Day9 {


    data class Movement(val direction: Char, val displacement: Int)

    private var headPosition: Pair<Int, Int> = Pair(0, 0)
    private var tailPosition: Pair<Int, Int> = Pair(0, 0)
    private var positions: MutableList<Pair<Int, Int>> = mutableListOf<Pair<Int, Int>>().apply {
        (0 until 10).forEach { _ -> add(Pair(0,0)) }
    }
    private val visitedPositions: MutableSet<Pair<Int, Int>> = mutableSetOf()

    private val input: List<Movement> = readFromResources("9").split(("\n")).mapNotNull { line ->
        Regex("([RULD]) (\\d+)").find(line)?.let {
            val (direction, displacement) = it.destructured
            Movement(direction[0], displacement.toInt())
        }
    }

    private fun Pair<Int, Int>.move(direction: Char): Pair<Int, Int> {
        return when (direction) {
            'U' -> Pair(first, second + 1)
            'D' -> Pair(first, second - 1)
            'R' -> Pair(first + 1, second)
            else -> Pair(first - 1, second)
        }
    }

    private fun Pair<Int, Int>.follow(head: Pair<Int, Int>): Pair<Int, Int> {
        return when {
            head.first == first && abs(head.second - second) >= 2 -> Pair(
                first,
                second + if (head.second > second) 1 else -1
            )

            head.second == second && abs(head.first - first) >= 2 -> Pair(
                first + if (head.first > first) 1 else -1,
                second
            )

            (head.second != second && abs(head.first - first) >= 2)
                    || (head.first != first && abs(head.second - second) >= 2) -> Pair(
                first + if (head.first > first) 1 else -1,
                second + if (head.second > second) 1 else -1
            )

            else -> this
        }
    }


    @Test
    fun test1() {
        input.forEach { movement ->
            println(movement)
            (0 until movement.displacement).forEach {
                headPosition = headPosition.move(movement.direction)
                tailPosition = tailPosition.follow(headPosition)
                println("$headPosition - $tailPosition")
                visitedPositions.add(tailPosition)
            }
            println("================")
        }
        println(visitedPositions.size)
    }

    @Test
    fun test2() {
        input.forEach { movement ->
            (0 until movement.displacement).forEach {
                positions[0] = positions[0].move(movement.direction)
                positions.takeLast(9).forEachIndexed{ index, pair ->
                    positions[index + 1] = positions[index + 1].follow(positions[index])
                    if (index == 8){
                        visitedPositions.add(positions[index + 1])
                    }
                }

            }
        }
        println(visitedPositions.size)
    }
}

