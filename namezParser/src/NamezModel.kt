
data class EmriMin(val e: String, val f: Int, val p : Map<Int, Int>)


data class Emri(val emri: String, val mashkull: Boolean, val frequency: Int, val perYear : Map<Int, Int>){
    fun toEmriMin() : EmriMin {
       return EmriMin(emri, frequency, perYear)
    }
}


data class MutableEmri(val emri: String, val mashkull: Boolean, var frequency: Int, val perYear : MutableMap<Int, Int>){
    fun toEmri() : Emri{
        return Emri(emri, mashkull, frequency, perYear)
    }
}


data class EmriSimple(val emri: String, val gjinia: String, val frequency: Int, val year : Int){
    fun toMutableEmri() : MutableEmri {
        return MutableEmri(emri,
            "m".contentEquals(gjinia.trim().toLowerCase()),
            frequency, mutableMapOf<Int, Int>().apply {
            put(year, frequency)
        })
    }
}
