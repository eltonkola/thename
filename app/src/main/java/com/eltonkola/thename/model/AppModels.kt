package com.eltonkola.thename.model

import android.os.Parcelable
import com.eltonkola.adapterz_lib.BaseComposedDataItem
import com.eltonkola.adapterz_lib.BaseDataItem
import com.eltonkola.thename.model.db.Emri
import kotlinx.android.parcel.Parcelize

//do e ruajme tek pref
@Parcelize
enum class Gjinia(val vlera: String) : Parcelable {

    MASHKUll("m"), FEMER("f"), ALL("a");

    companion object {
        private val typeMap = values().associateBy(Gjinia::vlera)
        fun getGjinia(type: String?) = typeMap[type] ?: ALL
    }

}

data class ExploreItem(val mCount: Int, val fCount: Int) : BaseDataItem


data class FavoritedItem(val emrat: List<FavoriteItem>) : BaseComposedDataItem {
    override fun getChildren(): List<BaseDataItem> {
        return emrat
    }
}

data class FavoriteItem(val emri: Emri) : BaseDataItem


data class ThumbedListItem(val cards: List<ThumbedSubListItem>) : BaseComposedDataItem {
    override fun getChildren(): List<BaseDataItem> {
        return cards
    }
}

data class ThumbedSubListItem(val emrat: List<ThumbedSubListElementItem>) : BaseComposedDataItem {
    override fun getChildren(): List<BaseDataItem> {
        return emrat
    }
}

data class ThumbedSubListElementItem(val emri: Emri) : BaseDataItem


data class ListAllItem(val emri: String) : BaseDataItem
