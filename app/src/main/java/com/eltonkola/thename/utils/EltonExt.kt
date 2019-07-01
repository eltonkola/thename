package com.eltonkola.thename.utils

import android.content.res.Resources
import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*

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


fun Fragment.getStatusbarHeight(): Int {
    var statusBarHeight = 24.dpToPx
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        statusBarHeight = resources.getDimensionPixelSize(resourceId)
    }
    return statusBarHeight
}

val Int.pxToDp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.dpToPx: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()


fun getRandomColor(): Int {
    val rnd = Random()
    return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
}


val Boolean.visibility: Int
    get() {
        return if (this) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

val Boolean.visibilityInvisibility: Int
    get() {
        return if (this) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }

