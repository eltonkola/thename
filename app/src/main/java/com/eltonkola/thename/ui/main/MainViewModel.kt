package com.eltonkola.thename.ui.main

import androidx.lifecycle.MutableLiveData
import com.eltonkola.adapterz_lib.BaseDataItem
import com.eltonkola.thename.data.DataManager
import com.eltonkola.thename.data.PreManager
import com.eltonkola.thename.model.Gjinia
import com.eltonkola.thename.utils.BaseModel
import com.eltonkola.thename.utils.autoDispose
import timber.log.Timber

class MainViewModel(val dataManager: DataManager, val preManager: PreManager) : BaseModel() {

    val menu = MutableLiveData<List<BaseDataItem>>()
    val emri = MutableLiveData<String>()
    val mbiemri = MutableLiveData<String>()
    val gjinia = MutableLiveData<Gjinia>()

    fun loadMenu() {
        dataManager.loadMenu().subscribe({
            Timber.d("Nr elements: ${it.size}")
            menu.postValue(it)
        }, {
            it.printStackTrace()
        }).autoDispose(this)

        mbiemri.postValue(preManager.getMbiemri())
        emri.postValue(preManager.getEmri())
        gjinia.postValue(preManager.getGjinia())
    }

    fun updateLastName(mbiemriVal: String) {
        preManager.setMbiemri(mbiemriVal)
        mbiemri.postValue(mbiemriVal)
    }

    fun updateGjinia(gjiniaValue: Gjinia) {
        preManager.setGjinia(gjiniaValue)
        gjinia.postValue(gjiniaValue)
    }

}


