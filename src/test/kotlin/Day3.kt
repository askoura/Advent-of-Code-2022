import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day3 {

    val input = mutableListOf<String>()

    fun charToInt(char: Char): Int {
        return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(char) + 1
    }

    @BeforeEach
    fun captureInput() {
        input.addAll(
            Utils::class.java.getResource("3").readText()
                .split("\n")
        )
    }

    private fun Pair<String, String>.commonChar(): Char {
        return first.toCharArray().intersect(second.toCharArray().toSet()).first()
    }


    @Test
    fun test1() {
        print(input
            .map { string ->
                Pair(string.substring(0, string.length / 2), string.substring(string.length / 2, string.length))
            }
            .sumOf { charToInt(it.commonChar()) })

    }
    @Test
    fun test2() {
       println(input
           .chunked(3)
           .map { it[0].toCharArray().intersect(it[1].toCharArray().toSet()).intersect(it[2].toCharArray().toSet()) }
           .sumOf { charToInt(it.first()) })
    }


}