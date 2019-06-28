package com.eltonkola.thename.db


import androidx.lifecycle.LiveData
import androidx.room.*
import com.eltonkola.thename.model.db.Emri
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface EmriDao {

//    @Query("SELECT * FROM msg where m_with = :withUser order by m_kur desc")
//    fun getMsgWithUser(withUser: String): LiveData<List<MsgElement>>

//    @Query("SELECT DISTINCT m_with FROM msg order by m_kur desc")
//    fun getUserTakingWith(): Flowable<List<String>>

    @Query("SELECT * FROM namez")
    fun getAll(): LiveData<List<Emri>>

    @Query("SELECT * FROM namez where thumb == 0 ")
    fun explore(): Single<List<Emri>>


    @Update
    fun updateEmri(emri: Emri): Completable

    @Insert
    fun insert(emri: Emri): Completable

    @Delete
    fun delete(emri: Emri): Completable

    @Query("SELECT * FROM namez where favorite == 1 order by name desc")
    fun getFavorites(): List<Emri>


    @Query("Update namez set thumb = 2 where name == :emri ")
    fun thumbUp(emri: String): Completable

    @Query("Update namez set thumb = 1 where name == :emri ")
    fun thumbDown(emri: String): Completable

    @Query("Update namez set favorite = :fav where name == :emri ")
    fun favorite(emri: String, fav: Boolean): Completable

    @Query("SELECT count(*) FROM namez where male == 'true' and thumb == 0")
    fun countExploreM(): Int

    @Query("SELECT count(*) FROM namez where male == 'false' and thumb == 0")
    fun countExploreF(): Int

    @Query("SELECT * FROM namez where thumb == 2 order by name desc")
    fun getThumbedUp(): List<Emri>

}

