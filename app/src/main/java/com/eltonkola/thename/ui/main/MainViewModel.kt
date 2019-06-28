package com.eltonkola.thename.ui.main

import androidx.lifecycle.MutableLiveData
import com.eltonkola.adapterz_lib.BaseDataItem
import com.eltonkola.thename.data.DataManager
import com.eltonkola.thename.model.FavoritedItem
import com.eltonkola.thename.utils.BaseModel
import com.eltonkola.thename.utils.autoDispose
import timber.log.Timber

class MainViewModel(val dataManager: DataManager) : BaseModel() {

    val menu = MutableLiveData<List<BaseDataItem>>()

    fun loadMenu() {
        dataManager.loadMenu().subscribe({
            Timber.d("Nr elements: ${it.size}")
            menu.postValue(it)
        }, {
            it.printStackTrace()
        }).autoDispose(this)
    }

}


