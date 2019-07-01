package com.eltonkola.thename.ui.explore

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.eltonkola.thename.data.DataManager
import com.eltonkola.thename.db.EmriDatabase
import com.eltonkola.thename.model.db.Emri
import com.eltonkola.thename.utils.BaseModel
import com.eltonkola.thename.utils.autoDispose

class ExploreViewModel(private val dataManager: DataManager, db: EmriDatabase) : BaseModel() {

    val dataList: LiveData<PagedList<Emri>>

    init {
        val factory: DataSource.Factory<Int, Emri> = db.emriAppDao().explore()
        val pagedListBuilder = LivePagedListBuilder(factory, 50)
        dataList = pagedListBuilder.build()
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
