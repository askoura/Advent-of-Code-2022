class Utils {

    companion object{
        fun readFromResources(filename: String) = Utils::class.java.getResource(filename)!!.readText()
    }
}