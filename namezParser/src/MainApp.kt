import java.io.File

fun main(){

    val tereEmrat = mutableMapOf<Int, List<EmriSimple>>()

    File("dataz/").walk().forEach {
        if(!it.isDirectory){
            val parser = FileParser(it.absolutePath)
            tereEmrat[parser.viti] = parser.parseEmrat()
        }
    }

    val converter = NamezConverter(tereEmrat)
    converter.convertData()



    println("tereEmrat : ${tereEmrat.size}")

}