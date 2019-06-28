import com.google.gson.Gson
import java.io.File
import java.io.FileWriter
import java.io.IOException
import com.google.gson.stream.JsonWriter
import java.util.*
import java.util.concurrent.TimeUnit


class NamezConverter(val data : Map<Int, List<EmriSimple>>) {

    fun convertData() {

        data.forEach { viti, list ->
            covertViti(viti, list)
        }

        val allEmratFinal = allNames.map { it.value.toEmri() }.toList()
        println("allEmrat - ${allEmratFinal.size}")

//        serializeAAAA(allEmratFinal , "output/all.json")

//        val meshkuj = allEmratFinal.filter { it.mashkull}.map { it.toEmriMin() }.sortedBy { it.f}
//        val femra = allEmratFinal.filter { !it.mashkull}.map { it.toEmriMin() }.sortedBy { it.f}
//
//        println("meshkuj - ${meshkuj.size}")
//        println("femra - ${femra.size}")
//
//        saveOnDisk(meshkuj, femra)

        createSqlite(allEmratFinal)
    }

//    private fun saveOnDisk(meshkuj: List<EmriMin>, femra: List<EmriMin>){
//        serialize(meshkuj, "output/meshkuj.json")
//        serialize(femra, "output/femra.json")
//    }

    private fun createSqlite(emrat: List<Emri>){

        val startDate =  Date()
        val db = DbManager()
        db.createNewDatabase()
        db.createTable()


        emrat.forEach {
            db.insertEmri(it)
        }

        val now =  Date()
        val millis = now.time - startDate.time
        val sa = String.format("%02d min, %02d sec",
            TimeUnit.MILLISECONDS.toMinutes(millis),
            TimeUnit.MILLISECONDS.toSeconds(millis) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        )

        println("*******************************")
        println("export in $sa")
        println("*******************************")

    }

    private val gson = Gson()

    private fun serializeAAAA(cafre: List<Emri>, emri: String){
//        gson.toJson(cafre, FileWriter(emri))


        try {
            val writer = JsonWriter(FileWriter(emri))
            writer.beginArray()
            for (u in cafre) {
                writer.beginObject()
                writer.name("e").value(u.emri)
                writer.name("f").value(u.frequency)
                writer.name("m").value(u.mashkull)
                writer.name("p").jsonValue(gson.toJson(u.perYear))
                writer.endObject()
            }
            writer.endArray()
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


    private fun serialize(cafre: List<EmriMin>, emri: String){
//        gson.toJson(cafre, FileWriter(emri))


        try {
            val writer = JsonWriter(FileWriter(emri))
            writer.beginArray()
            for (u in cafre) {
                writer.beginObject()
                writer.name("e").value(u.e)
                writer.name("f").value(u.f)
                writer.name("p").jsonValue(gson.toJson(u.p))
                writer.endObject()
            }
            writer.endArray()
             writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private val allNames = mutableMapOf<String, MutableEmri>()

    private fun covertViti(viti: Int, list: List<EmriSimple>){
        list.forEach {emri->
            val eksistues = allNames[emri.emri]
            eksistues?.let {eksistues ->
                //update existing one
                eksistues.frequency +=  emri.frequency
                eksistues.perYear[viti] = emri.frequency
            } ?: run{
                //create new one
                allNames[emri.emri] = emri.toMutableEmri()
            }
        }
    }



}
