package com.eltonkola.thename.db


import androidx.paging.DataSource
import androidx.room.*
import com.eltonkola.thename.model.db.Emri
import io.reactivex.Completable

@Dao
interface EmriDao {

    @Query("SELECT * FROM namez ORDER BY frequency desc")
    fun all(): DataSource.Factory<Int, Emri>


    @Query("SELECT * FROM namez WHERE male == 'true' ORDER BY frequency desc")
    fun allMale(): DataSource.Factory<Int, Emri>

    @Query("SELECT * FROM namez WHERE male == 'false' ORDER BY frequency desc")
    fun allFemale(): DataSource.Factory<Int, Emri>

    @Query("SELECT * FROM namez WHERE favorite == 1 ORDER BY frequency desc")
    fun allFav(): DataSource.Factory<Int, Emri>

    @Query("SELECT * FROM namez WHERE thumb == 2 ORDER BY frequency desc")
    fun allThumbed(): DataSource.Factory<Int, Emri>

    @Query("SELECT * FROM namez WHERE name LIKE :query ORDER BY frequency desc")
    fun kerko(query: String): DataSource.Factory<Int, Emri>


    @Query("SELECT * FROM namez WHERE thumb == 0 ORDER BY frequency desc")
    fun explore(): DataSource.Factory<Int, Emri>

    @Query("SELECT * FROM namez WHERE thumb == 0 and male == 'true' ORDER BY frequency desc")
    fun exploreM(): DataSource.Factory<Int, Emri>

    @Query("SELECT * FROM namez WHERE thumb == 0 and male == 'false' ORDER BY frequency desc")
    fun exploreF(): DataSource.Factory<Int, Emri>

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

