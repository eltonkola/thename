package com.eltonkola.thename.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.eltonkola.thename.db.EmriDatabase
import com.eltonkola.thename.model.db.Emri
import com.eltonkola.thename.utils.BaseModel


class ListViewModel(private val db: EmriDatabase) : BaseModel() {

    val allList: LiveData<PagedList<Emri>>

    private val queryType = MutableLiveData<Shfleto>()

    enum class Shfleto(val vlera: String) {
        ALL("all"), MALE("male"), FEMALE("female"), FAVORITES("favorites"), THUMBED("thumbed"), SEARCH(
            "search"
        );

        companion object {
            private val typeMap = Shfleto.values().associateBy(Shfleto::vlera)
            fun getType(type: String?) = typeMap[type] ?: Shfleto.ALL
        }
    }

    private var searchString = ""

    init {

        allList = Transformations.switchMap(queryType) { type ->
            LivePagedListBuilder(
                when (type) {
                    Shfleto.ALL -> db.emriAppDao().all()
                    Shfleto.MALE -> db.emriAppDao().allMale()
                    Shfleto.FEMALE -> db.emriAppDao().allFemale()
                    Shfleto.FAVORITES -> db.emriAppDao().allFav()
                    Shfleto.THUMBED -> db.emriAppDao().allThumbed()
                    Shfleto.SEARCH -> db.emriAppDao().kerko(searchString)
                }, 50
            ).build()

        }
    }

    fun loadData(type: Shfleto, kerko: String? = null) {
        kerko?.let {
            searchString = it
        }
        queryType.postValue(type)
    }

}
