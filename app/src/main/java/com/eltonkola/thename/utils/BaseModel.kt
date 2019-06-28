package com.eltonkola.thename.utils

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    init {


    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}