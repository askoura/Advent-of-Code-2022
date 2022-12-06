import org.junit.jupiter.api.Test

class Day2 {


    enum class Result(val score: Int) {
        WIN(6), LOSE(0), DRAW(3)
    }

    enum class MOVE(val score: Int) {
        ROCK(1), PAPER(2), SCISSORS(3)
    }

    fun charToMove(char: Char): MOVE {
        return when (char) {
            'A', 'X' -> MOVE.ROCK
            'B', 'Y' -> MOVE.PAPER
            'C', 'Z' -> MOVE.SCISSORS
            else -> throw Exception("UNknown char: " + char)
        }
    }

    fun charToResult(char: Char): Result {
        return when (char) {
             'X' -> Result.LOSE
             'Y' -> Result.DRAW
             'Z' -> Result.WIN
            else -> throw Exception("UNknown char: " + char)
        }
    }

    fun moveToResult(first: MOVE,result: Result): MOVE {
        return when (first) {
            MOVE.ROCK -> when (result) {
                Result.DRAW -> MOVE.ROCK
                Result.LOSE -> MOVE.SCISSORS
                Result.WIN -> MOVE.PAPER
            }

            MOVE.PAPER -> when (result) {
                Result.DRAW -> MOVE.PAPER
                Result.LOSE -> MOVE.ROCK
                Result.WIN -> MOVE.SCISSORS
            }

            MOVE.SCISSORS -> when (result) {
                Result.DRAW -> MOVE.SCISSORS
                Result.LOSE -> MOVE.PAPER
                Result.WIN -> MOVE.ROCK
            }
        }
    }


    private fun result(first: MOVE, second: MOVE): Result {
        return when (second) {
            MOVE.ROCK -> when (first) {
                MOVE.ROCK -> Result.DRAW
                MOVE.PAPER -> Result.LOSE
                MOVE.SCISSORS -> Result.WIN
            }

            MOVE.PAPER -> when (first) {
                MOVE.ROCK -> Result.WIN
                MOVE.PAPER -> Result.DRAW
                MOVE.SCISSORS -> Result.LOSE
            }

            MOVE.SCISSORS -> when (first) {
                MOVE.ROCK -> Result.LOSE
                MOVE.PAPER -> Result.WIN
                MOVE.SCISSORS -> Result.DRAW
            }
        }

    }

    private fun score(first: MOVE, second: MOVE): Int {
        return result(first, second).score + second.score
    }

    @Test
    fun test1() {
        val fileContent = Utils::class.java.getResource("2").readText()
        println(
            fileContent
                .split("\n")
                .sumOf { score(charToMove(it[0]), charToMove(it[2])) }
        )
    }


    @Test
    fun test3() {
        val fileContent = Utils::class.java.getResource("2").readText()
        println(
            fileContent
                .split("\n")
                .sumOf {
                    val firstMove = charToMove(it[0])
                    val result = charToResult(it[2])
                    val secondMove = moveToResult(firstMove, result)
                    score(firstMove, secondMove)
                }
        )
    }
}