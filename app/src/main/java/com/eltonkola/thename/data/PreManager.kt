package com.eltonkola.thename.data

import android.content.Context
import com.eltonkola.thename.model.Gjinia
import com.eltonkola.thename.model.db.Emri

class PreManager(applicationContext: Context) {

    companion object {
        private const val PREFZ = "prefz"
        private const val GJINIA = "gjinia"
        private const val STAR = "star"
    }

    private val sharedPreferences = applicationContext.getSharedPreferences(PREFZ, 0)

    fun getGjinia(): Gjinia {
        return Gjinia.getGjinia(sharedPreferences.getString(GJINIA, ""))
    }

    fun setGjinia(value: Gjinia) {
        sharedPreferences.edit().putString(GJINIA, value.vlera).apply()
    }

    fun setStar(name: Emri) {
        sharedPreferences.edit().putString(STAR, name.name).apply()
    }

    fun isStar(name: Emri): Boolean {
        return name.name.contentEquals(sharedPreferences.getString(STAR, "") ?: "")
    }

}
