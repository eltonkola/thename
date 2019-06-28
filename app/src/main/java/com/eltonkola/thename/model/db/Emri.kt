package com.eltonkola.thename.model.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.eltonkola.adapterz_lib.BaseDataItem
import kotlinx.android.parcel.Parcelize

@Entity(
    indices = arrayOf(Index(value = ["favorite", "frequency", "thumb", "male"])),
    tableName = "namez"
)
@Parcelize
data class Emri(
    @PrimaryKey @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "male") var male: Boolean,
    @ColumnInfo(name = "favorite") var favorite: Boolean,
    @ColumnInfo(name = "thumb") var thumb: Int,
    @ColumnInfo(name = "frequency") var frequency: Int,
    @ColumnInfo(name = "peryear") var peryear: String
) : BaseDataItem, Parcelable {

//    @PrimaryKey(autoGenerate = true)
//    var uid: Int = 0
//
//    fun getPerYerData() {
//        //return Map<Int, Int>
//    }

}
