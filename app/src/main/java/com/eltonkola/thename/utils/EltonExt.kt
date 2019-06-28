package com.eltonkola.thename.utils

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.autoDispose(vm: BaseModel) {
    if (this is CompositeDisposable) {
        throw IllegalArgumentException("Cannot put a composite disposable into another one.")
    }
    vm.compositeDisposable.add(this)
}

fun Fragment.toast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

fun Boolean.visibility(): Int {
    return if (this) View.VISIBLE else View.GONE
}
