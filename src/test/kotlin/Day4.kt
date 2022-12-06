import Utils.Companion.readFromResources
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
typealias IntPair = Pair<Int,Int>

class Day4 {



    private val assignments = mutableListOf<Pair<IntPair, IntPair>>()
    private val regex = Regex("(\\d+)-(\\d+),(\\d+)-(\\d+)")

    @BeforeEach
    fun getInput() {

        val content = readFromResources("4")
        val lines = content.split("\n")
        lines.forEach { line ->
            regex.find(line)?.let {
                val (first, second, third, fourth) = it.destructured
                assignments.add(Pair(Pair(first.toInt(),second.toInt()), Pair(third.toInt(),fourth.toInt())))
            }
        }
    }

    private fun Pair<IntPair, IntPair>.anyContainsAny():Int{
        return if ((first.first <= second.first && first.second >= second.second)
                || (first.first >= second.first && first.second <= second.second))
            1
        else
                0
    }

    private fun Pair<IntPair, IntPair>.anyOverlap():Int{
        return if ((first.second >= second.first && first.first <= second.first)
            || (second.second >= first.first && second.first <= first.first))
            1
        else
            0
    }



    @Test
    fun test1() {
        println(assignments.sumOf { it.anyContainsAny()})

    }


    @Test
    fun test2() {
        println(assignments.sumOf { it.anyOverlap()})
    }
}

