import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day5 {

    data class Instruction(val from: Int, val to: Int, val quantity: Int)

    val crates = mutableListOf(mutableListOf<Char>())
    val instructions = mutableListOf<Instruction>()

    init {
        (0 until 9).map {
            crates.add(mutableListOf())
        }
    }

    @BeforeEach
    fun getInput() {
        val regex = Regex("\\[(\\S)\\].*")
        val instructionRegex = Regex("move (\\d+) from (\\d) to (\\d)")
        val cratesContent = Utils::class.java.getResource("5-crates").readText()
        val instructionsContent = Utils::class.java.getResource("5").readText()
        val lines = cratesContent.split("\n")
        val instructionLines = instructionsContent.split("\n")
        lines.take(lines.size - 1).forEach { line ->
            (0 until 9).map { iterator ->
                val input = if (iterator == 8) {
                    line.substring(iterator * 4, (iterator + 1) * 4 - 1)
                } else {
                    line.substring(iterator * 4, (iterator + 1) * 4)
                }
                regex.find(input)?.let {
                    val (age) = it.destructured
                    crates[iterator].add(age[0])
                }
            }
        }
        (0 until 9).map { iterator ->
            crates[iterator].reverse()
        }
        instructionLines.forEach {line->
            instructionRegex.find(line)?.let {
                val (quantity, from, to) = it.destructured
                instructions.add(Instruction(from.toInt() - 1,to.toInt() - 1,quantity.toInt()))
            }
        }
    }

    private fun printCrates(){
        val length:Int = crates.maxBy { it.size }.size
        (0 until length).reversed().map {levelIndex->
            (0 until 9).map {crateIndex->
                if(crates[crateIndex].size > levelIndex)
                {
                    print("[" + crates[crateIndex][levelIndex] + "]")
                } else {
                    print("   ")
                }
            }
            println()
        }
        (0 until 9).map {
            print(" ${it + 1} ")
        }
        println("")
        println("---------------------------")

    }
    private fun printInstruction(instruction: Instruction){
        instruction.run {
            println("move $quantity from ${from + 1} to ${to + 1}")
        }

    }


    @Test
    fun test1() {
        printCrates()
        instructions.forEach {
            printInstruction(it)
            val from = crates[it.from]
            val to = crates[it.to]
            val newFrom = from.dropLast(it.quantity)
            to.addAll(from.takeLast(it.quantity).reversed())
            from.clear()
            from.addAll(newFrom)
            printCrates()
        }
    }


    @Test
    fun test2() {
        printCrates()
        instructions.forEach {
            printInstruction(it)
            val from = crates[it.from]
            val to = crates[it.to]
            val newFrom = from.dropLast(it.quantity)
            to.addAll(from.takeLast(it.quantity))
            from.clear()
            from.addAll(newFrom)
            printCrates()
        }
    }
}

