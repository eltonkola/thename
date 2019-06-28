package com.eltonkola.thename.ui.explore

import androidx.lifecycle.MutableLiveData
import com.eltonkola.thename.data.DataManager
import com.eltonkola.thename.db.EmriDatabase
import com.eltonkola.thename.model.db.Emri
import com.eltonkola.thename.utils.BaseModel
import com.eltonkola.thename.utils.autoDispose
import timber.log.Timber

class ExploreViewModel(val dataManager: DataManager, private val db: EmriDatabase) : BaseModel() {

    val dataList = MutableLiveData<List<Emri>>()

    fun loadData() {
        dataManager.loadExplore().subscribe({
            Timber.d("Nr elements: ${it.size}")
            dataList.postValue(it)
        }, {
            it.printStackTrace()
        }).autoDispose(this)

    }


    fun thumbUp(pos: Int) {
        val emri = dataList.value?.get(pos)
        emri?.let { emrix ->
            emrix.thumb = 2
            dataManager.thumbUp(emrix.name).subscribe({
            }, {
            }).autoDispose(this)
        }
    }

    fun thumbDown(pos: Int) {
        val emri = dataList.value?.get(pos)
        emri?.let { emrix ->
            emrix.thumb = 1
            dataManager.thumbDown(emrix.name).subscribe({
            }, {
            }).autoDispose(this)
        }
    }

    fun fav(pos: Int) {
        val emri = dataList.value?.get(pos)
        emri?.let { emrix ->
            emrix.favorite = !emrix.favorite
            if (emrix.favorite) {
                emrix.thumb = 0
            }
            dataManager.fav(emrix.name, emrix.favorite).subscribe({
            }, {
            }).autoDispose(this)
        }
    }


}
