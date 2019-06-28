import java.io.File

class FileParser(val name: String) {

    private val file = File(name)
    val viti = name.substring(name.lastIndexOf("/yob") + 4, name.indexOf(".txt")).toInt()

    private val emrat = mutableListOf<EmriSimple>()


    fun parseEmrat() : List<EmriSimple> {

        file.forEachLine { rreshti->
            val data = rreshti.split(",")
            emrat.add(EmriSimple(data[0], data[1], data[2].toInt(), viti))
        }

        println("viti $viti - emra: ${emrat.size}")

        return emrat

    }


}