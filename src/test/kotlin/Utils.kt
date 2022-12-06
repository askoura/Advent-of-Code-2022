class Utils {
    fun readFromResources(filename:String){
        val fileContent = Utils::class.java.getResource("1-A").readText()
        print(fileContent)
    }
}