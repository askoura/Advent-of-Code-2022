import Utils.Companion.readFromResources
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


sealed class DeviceContent(open val name:String) {
    data class DeviceFile(override val name: String, val size: Int) : DeviceContent(name)
    data class DeviceDirectory(override val name: String, val contents: MutableList<DeviceContent> = mutableListOf()) :
        DeviceContent(name)
}


class Day7 {


    private val content = DeviceContent.DeviceDirectory("/", mutableListOf())
    private val currentLocation = mutableListOf(content)

    private val inputLines = readFromResources("7").split("\n")

    private fun printContent(deviceDirectory: DeviceContent.DeviceDirectory, level: Int = 0) {
        (0 until level).forEach { _ -> print("    ") }
        println("- ${deviceDirectory.name} (dir)")

        deviceDirectory.contents.sortedBy { it.name }.forEach {
            (it as? DeviceContent.DeviceFile)?.let {
                (0 until level + 1).forEach { _ -> print("    ") }
                println("- ${it.name} (file, size=${it.size})")
            }
            (it as? DeviceContent.DeviceDirectory)?.let {
                printContent(it, level + 1)
            }
        }
    }

    private fun DeviceContent.DeviceDirectory.size(list:MutableList<DeviceContent.DeviceFile> = mutableListOf()): Int {
        val item = DeviceContent.DeviceFile(name, contents.filterIsInstance<DeviceContent.DeviceDirectory>()
            .sumOf { it.size(list) } + contents.filterIsInstance<DeviceContent.DeviceFile>().sumOf { it.size })
        list.add(item)
        return item.size
    }

    @BeforeEach
    fun process() {
        for (inputLine in inputLines) {
            // this is a command
            if (inputLine == "\$ cd ..") {

                val newList = currentLocation.take(currentLocation.size - 1)
                currentLocation.clear()
                currentLocation.addAll(newList)
            } else if (inputLine == "\$ cd /" || inputLine == "\$ ls" || inputLine.startsWith("dir")) {
                // Don't do shit
            } else if (inputLine.startsWith("\$ cd")) {
                val directory = DeviceContent.DeviceDirectory(inputLine.substring(5))
                currentLocation.last().contents.add(directory)
                currentLocation.add(directory)
            } else {
                Regex("(\\d+) (.+)").find(inputLine)?.let {
                    val (size, name) = it.destructured
                    currentLocation.last().contents.add(DeviceContent.DeviceFile(name, size.toInt()))
                }
            }
        }
        printContent(content)
    }


    @Test
    fun test1() {
        val list = mutableListOf<DeviceContent.DeviceFile>()
        println(content.size(list))
        println(list.filter { it.size < 100000 }.sumOf { it.size })


    }

    @Test
    fun test2() {
        val list = mutableListOf<DeviceContent.DeviceFile>()
        val sizeToFreeUp = 30000000 - (70000000 - content.size(list))
        println(sizeToFreeUp)
       list.filter { it.size >= sizeToFreeUp }.sortedBy {
           it.size
       }.forEach {
           println(it)
       }
    }
}
