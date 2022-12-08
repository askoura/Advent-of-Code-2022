import Utils.Companion.readFromResources
import org.junit.jupiter.api.Test

class Day8 {


    private val input = readFromResources("8").split(("\n")).map { line -> line.toCharArray().map {  it.digitToInt() } }




    private fun getNthColumn(n: Int): List<Int> = input.map { it[n] }
    private fun List<Int>.seen(value: Int, index :Int): Boolean {
        return index <= indexOfFirst { it >= value } || index >= indexOfLast { it >= value }
    }

    private fun List<Int>.scenicScore(value: Int, index :Int): Int {
        println(this)
        val before = if(index == 0) 0 else subList(0, index).indexOfLast { it >= value }
        val after = if(index == size - 1)  index else subList(index + 1, size).indexOfFirst { it >= value }
        val beforeScore = if(before == -1)
            index else index - before
        val afterScore = if(after == -1 || after == index)
            size - 1 - index else after + 1
        return beforeScore * afterScore
    }


    @Test
    fun test1() {
        val breadth = input.first().size
        var seen = 0
        for (i in input.indices) {
          for (j in 0 until breadth){
              val tree = input[i][j]
              if(input[i].seen(tree, j)
                  || getNthColumn(j).seen(tree, i)){
                  seen++
              }
          }
        }
        println(seen)
    }

    @Test
    fun test2() {
        val scenicScores = mutableListOf<Int>()
        val breadth = input.first().size
        for (i in input.indices) {
            for (j in 0 until breadth){
                val tree = input[i][j]
                val result = input[i].scenicScore(tree, j) * getNthColumn(j).scenicScore(tree, i)
                scenicScores.add(result)
            }
        }
        println(scenicScores.max())
    }
}

