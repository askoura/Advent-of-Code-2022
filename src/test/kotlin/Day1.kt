import Utils.Companion.readFromResources
import org.junit.jupiter.api.Test

class Day1 {
    @Test
    fun test1() {
        val fileContent = readFromResources("1-A")
        println(
            fileContent
                .split("\n\n")
                .maxOfOrNull { it ->
                    it.split(("\n"))
                        .sumOf { it.toInt() }
                }
        )
    }

    @Test
    fun test2() {
        val fileContent = readFromResources("1-A")
        val calories:List<Int> = fileContent.split("\n\n").map { it ->
            it.split(("\n"))
                .sumOf { it.toInt() }
        }
        var top1 = 0
        var top2 = 0
        var top3 = 0

       for (i in calories.indices){
           val calory = calories[i]
           if (calory > top1){
               top3 = top2
               top2 = top1
               top1 = calory
           } else if(calory > top2){
               top3 = top2
               top2 = calory
           } else if(calory > top3){
               top3 = calory
           }
       }
        println(top1 + top2 + top3)
    }
}