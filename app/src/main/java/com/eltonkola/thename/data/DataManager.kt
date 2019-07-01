package com.eltonkola.thename.data

import android.content.Context
import com.eltonkola.adapterz_lib.BaseDataItem
import com.eltonkola.thename.db.EmriDatabase
import com.eltonkola.thename.model.*
import com.eltonkola.thename.model.db.Emri
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


data class DataManager(val context: Context, val settings: PreManager, val db: EmriDatabase) {


    //load main page menu
    fun loadMenu(): Single<List<BaseDataItem>> {

        return Single.create<List<BaseDataItem>> {

            val preferuara = db.emriAppDao().getFavorites()
            val mCount = db.emriAppDao().countExploreM()
            val fCount = db.emriAppDao().countExploreF()
            val thumbed = db.emriAppDao().getThumbedUp()

            Timber.d(">>>>>>>>>>>>>> menu start >>>>>>>>>>>>>")
            Timber.d("preferuara: ${preferuara.size}")
            Timber.d("mCount: $mCount")
            Timber.d("fCount: $fCount")
            Timber.d("thumbed: ${thumbed.size}")
            Timber.d(">>>>>>>>>>>>>> menu end >>>>>>>>>>>>>")

            val menuItems = mutableListOf<BaseDataItem>()
            if (preferuara.isNotEmpty()) {
                menuItems.add(FavoritedItem(preferuara.map { FavoriteItem(it) }))
            }

            if (mCount > 0 || fCount > 0) {
                menuItems.add(ExploreItem(mCount, fCount))
            }

            if (thumbed.isNotEmpty()) {
                menuItems.add(ThumbedListItem(thumbed.chunked(5).map {
                    ThumbedSubListItem(it.map {
                        ThumbedSubListElementItem(
                            it
                        )
                    })
                }))
            }
            menuItems.add(ListAllItem("all"))


            it.onSuccess(menuItems)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())


    }


    fun thumbUp(name: String): Completable {
        return db.emriAppDao().thumbUp(name)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun thumbDown(name: String): Completable {
        return db.emriAppDao().thumbDown(name)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun fav(name: String, favorite: Boolean): Completable {
        return db.emriAppDao().favorite(name, favorite)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }


}