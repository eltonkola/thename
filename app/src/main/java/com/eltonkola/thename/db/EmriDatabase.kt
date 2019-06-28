package com.eltonkola.thename.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eltonkola.thename.db.EmriDao
import com.eltonkola.thename.model.db.Emri

@Database(entities = arrayOf(Emri::class), version = 1)
//@TypeConverters(Converters::class)
abstract class EmriDatabase : RoomDatabase() {
    abstract fun emriAppDao(): EmriDao
}