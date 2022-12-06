import org.junit.jupiter.api.Test

class Day6 {


    private val input = Utils::class.java.getResource("6")!!.readText().toCharArray().toList() ?: listOf()


    private fun isUnique(buffer: MutableList<Char>, char: Char): Boolean {
        return mutableListOf<Char>().apply {
            addAll(buffer.subList(1, buffer.size))
            add(char)
        }
            .distinct().size == buffer.size
    }

    private fun pushToBuffer(buffer: MutableList<Char>, char: Char, threshold: Int) {
        (0 until threshold - 1).forEach {
            buffer[it] = buffer[it + 1]
        }
        buffer[threshold - 1] = char
    }


    @Test
    fun test1() {
        val threshold = 14
        val buffer = mutableListOf<Char>()
        (0 until threshold).forEach {
            buffer.add(input[it])
        }
        println("at: $threshold - $buffer")
        input.subList(threshold, input.size).forEachIndexed { index, c ->
            if (isUnique(buffer, c)) {
                println("the index is ${index + threshold + 1}")
                println(buffer.subList(1, threshold).apply { add(c) })
                return
            } else {
                pushToBuffer(buffer, c, threshold)
            }
            println("at: ${index + threshold + 1} - $buffer")
        }
    }
}

