package com.eltonkola.thename.ui.details

import androidx.lifecycle.MutableLiveData
import com.eltonkola.thename.data.DataManager
import com.eltonkola.thename.data.PreManager
import com.eltonkola.thename.db.EmriDatabase
import com.eltonkola.thename.model.db.Emri
import com.eltonkola.thename.utils.BaseModel
import com.eltonkola.thename.utils.autoDispose


class DetailsViewModel(
    private val db: EmriDatabase,
    emri_fillestar: Emri,
    private val prefManager: PreManager,
    val dataManager: DataManager
) : BaseModel() {

    val emri = MutableLiveData<Emri>().apply {
        value = emri_fillestar
    }

    val mbiemri = MutableLiveData<String>().apply {
        value = prefManager.getMbiemri()
    }

    fun thumbUp() {

        emri.value?.let { emrix ->
            emrix.thumb = 2
            dataManager.thumbUp(emrix.name).subscribe({
                emri.postValue(emrix)
            }, {
                emri.postValue(emrix)
            }).autoDispose(this)
        }

    }

    fun thumbDown() {
        emri.value?.let { emrix ->
            emrix.thumb = 1
            dataManager.thumbDown(emrix.name).subscribe({
                emri.postValue(emrix)
            }, {
                emri.postValue(emrix)
            }).autoDispose(this)
        }
    }

    fun fav() {
        emri.value?.let { emrix ->
            emrix.favorite = !emrix.favorite
            if (emrix.favorite) {
                emrix.thumb = 0
            }
            dataManager.fav(emrix.name, emrix.favorite).subscribe({
                emri.postValue(emrix)
            }, {
                emri.postValue(emrix)
                it.printStackTrace()
            }).autoDispose(this)
        }
    }

    fun star() {
        emri.value?.let { emrix ->
            prefManager.setStar(emrix)

            emri.postValue(emrix)
        }
    }

    fun isStar(): Boolean {
        return prefManager.isStar(emri.value!!)
    }

}
