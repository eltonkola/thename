package com.eltonkola.thename.data

import android.content.Context
import com.eltonkola.thename.model.Gjinia
import com.eltonkola.thename.model.db.Emri

class PreManager(applicationContext: Context) {

    companion object {
        private const val PREFZ = "prefz"
        private const val GJINIA = "gjinia"
        private const val STAR = "star"
        private const val MBIEMRI = "mbiemri"


        const val MBIEMRI_EMPTY = "[No last name]"
        const val EMRI_EMPTY = "[No Name]"
    }

    private val sharedPreferences = applicationContext.getSharedPreferences(PREFZ, 0)

    fun getGjinia(): Gjinia {
        return Gjinia.getGjinia(sharedPreferences.getString(GJINIA, ""))
    }

    fun setGjinia(value: Gjinia) {
        sharedPreferences.edit().putString(GJINIA, value.vlera).apply()
    }

    fun getMbiemri(): String {
        return sharedPreferences.getString(MBIEMRI, MBIEMRI_EMPTY)
    }

    fun setMbiemri(value: String) {
        sharedPreferences.edit().putString(MBIEMRI, value).apply()
    }

    fun setStar(name: Emri) {
        sharedPreferences.edit().putString(STAR, name.name).apply()
    }

    fun getEmri(): String {
        return sharedPreferences.getString(STAR, EMRI_EMPTY)
    }

    fun isStar(name: Emri): Boolean {
        return name.name.contentEquals(sharedPreferences.getString(STAR, "") ?: "")
    }

}
