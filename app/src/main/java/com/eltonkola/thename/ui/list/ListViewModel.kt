package com.eltonkola.thename.ui.list

import androidx.lifecycle.LiveData
import com.eltonkola.thename.db.EmriDatabase
import com.eltonkola.thename.data.DataManager
import com.eltonkola.thename.model.db.Emri
import com.eltonkola.thename.utils.BaseModel

class ListViewModel(val dataManager: DataManager, private val db: EmriDatabase) : BaseModel() {


    var dataList: LiveData<List<Emri>> = db.emriAppDao().getAll()//getMsgWithUser(emri)

//    val dataList = MutableLiveData<List<BaseDataItem>>()

    fun loadData() {
//        dataManager.loadAll().subscribe({
//            Timber.d("Nr elements: ${it.size}")
//            dataList.postValue(it)
//        }, {
//            it.printStackTrace()
//        }).autoDispose(this)

    }

}
