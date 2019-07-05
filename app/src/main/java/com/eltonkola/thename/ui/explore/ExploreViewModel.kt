package com.eltonkola.thename.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.eltonkola.thename.data.DataManager
import com.eltonkola.thename.data.PreManager
import com.eltonkola.thename.db.EmriDatabase
import com.eltonkola.thename.model.Gjinia
import com.eltonkola.thename.model.db.Emri
import com.eltonkola.thename.utils.BaseModel
import com.eltonkola.thename.utils.autoDispose

class ExploreViewModel(
    private val dataManager: DataManager,
    db: EmriDatabase,
    private val preManager: PreManager
) : BaseModel() {

    val dataList: LiveData<PagedList<Emri>>

    private val gjinia = MutableLiveData<Gjinia>()

    init {

        gjinia.value = preManager.getGjinia()

        dataList = Transformations.switchMap(gjinia) { type ->
            LivePagedListBuilder(
                when (type) {
                    Gjinia.ALL -> db.emriAppDao().explore()
                    Gjinia.MASHKUll -> db.emriAppDao().exploreM()
                    Gjinia.FEMER -> db.emriAppDao().exploreF()
                }, 50
            ).build()

        }
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

    fun getMbiemri(): String {
        return preManager.getMbiemri()
    }


}
